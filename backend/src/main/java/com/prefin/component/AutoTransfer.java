package com.prefin.component;

import com.prefin.domain.money.AccountHistory;
import com.prefin.domain.money.Allowance;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import com.prefin.repository.money.AccountHistoryRepository;
import com.prefin.repository.money.AllowanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AutoTransfer {

    private final AllowanceRepository allowanceRepository;
    private final AccountHistoryRepository accountHistoryRepository;

    // 자동이체 로직
    // 용돈 설정 후 해당 테이블을 다 돌아보면서
    // 오늘 날짜와 비교, 일치한다면 해당 부모의 계좌에서 자식 계좌로 이체
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")  // "0 * * * * ?"
    @Transactional
    public void autoTransfer() {
        System.out.println("!!자동 이체 실행!!");
        List<Allowance> allowances = allowanceRepository.findAll();
        for (Allowance allowance:allowances) {
            Integer dayOfToday = LocalDateTime.now().getDayOfMonth();
            Long dayOfMonth = Long.valueOf(dayOfToday);
            Long payday = allowance.getPayday();

            int currentYear = LocalDateTime.now().getYear();
            int currentMonth = LocalDateTime.now().getMonthValue();

            YearMonth yearMonth = YearMonth.of(currentYear, currentMonth);
            int lastDayOfCurrentMonth = yearMonth.lengthOfMonth();

            if (payday > lastDayOfCurrentMonth) {
                payday = (long)lastDayOfCurrentMonth;
            }

            if (dayOfMonth.equals(payday)) {
                Parents parent = allowance.getParent();
                Child child = allowance.getChild();

                int balance = parent.getBalance();
                int allowanceAmount = allowance.getAllowanceAmount();

                // 부모 계좌에서 용돈과 잔액 비교 후 용돈 <= 잔액 이라면 돈을 차감하고 아니라면 cause error
                if (balance < allowanceAmount) {
                    System.out.println("잔액 부족");
                } else {
                    // 아래의 식을 계산하면 빌린 돈이 0일 땐 myDebt가 0이고 아니라면 한달치 이자가 나온다.
                    BigDecimal loanInterst = child.getLoanRate();

                    BigDecimal bigDebt = new BigDecimal(child.getLoanAmount());


                    BigDecimal totalDebt = loanInterst.multiply(bigDebt);
                    totalDebt = totalDebt.setScale(0, RoundingMode.FLOOR);
                    int myDebt = totalDebt.intValueExact();

                    // 만약 빌린 돈이 있다면 용돈에서 해당 원금과 한달 이자를 더한 금액을 용돈에서 뺀다.
                    if (allowanceAmount >= myDebt) {  // 용돈이 빚보다 크다면
                        allowanceAmount -= myDebt;
                        // 부모 계좌 잔액 차감
                        parent.transfer(allowanceAmount);

                        // 자식 계좌 잔액 증가시키기
                        child.addMoney(allowanceAmount);
                        child.resetLoan();
                        System.out.println("용돈 정상 지급");

                        // 거래 내역 추가
                        LocalDateTime now = LocalDateTime.now();

                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

                        String formattedDateTime = now.format(dateTimeFormatter);
                        String formattedTime = now.format(timeFormatter);

                        AccountHistory accountHistory = AccountHistory.builder().
                                child(child).
                                transactionDate(formattedDateTime).
                                transactionTime(formattedTime).
                                briefs("용돈").
                                deposit(String.valueOf(allowanceAmount)).
                                withdraw("0").
                                build();

                        accountHistoryRepository.save(accountHistory);

                    } else {  // 만약 빚이 용돈보다 크다면
                        // 부모 계좌 잔액 차감
                        parent.transfer(allowanceAmount);

                        // 자식 계좌에서 빚 탕감
                        child.subtractLoan(allowanceAmount);

                        System.out.println("대출 차감 후 남는 용돈이 없습니다.");
                    }
                }
            }
        }
    }
}

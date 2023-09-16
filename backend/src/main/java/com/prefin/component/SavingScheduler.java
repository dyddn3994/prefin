package com.prefin.component;

import com.prefin.domain.money.SavingHistory;
import com.prefin.domain.user.Child;
import com.prefin.repository.money.SavingRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.repository.user.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SavingScheduler {
    private final ChildRepository childRepository;
    private final SavingRepository savingRepository;

    // 매 1일 이자 입금
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")  // "0 * * * * ?" 매 분
    public void MonthlyInterest() {
        // 자녀의 저축금액으로 부모의 이율을 적용해서 돈을 준다.
        List<Child> childList = childRepository.findAll();

        // for문 돌면서 각 child의 저금 금액 가져와서 부모의 savingRate로 이자를 계산한다
        for (Child child : childList) {
            int savedMoney = child.getSavingAmount();
            if (savedMoney != 0) {
                BigDecimal savingInterest = child.getSavingRate();
                BigDecimal convertedSM = new BigDecimal(savedMoney);

                BigDecimal result = savingInterest.multiply(convertedSM);
                int monthlyInterest = result.setScale(0, RoundingMode.FLOOR).intValue();
                if (monthlyInterest > child.getParent().getMaxSavingAmount()) {
                    monthlyInterest = child.getParent().getMaxSavingAmount();
                }

                if (child.getParent().getBalance()- monthlyInterest < 0) {
                    return;
                }

                // 저축 금액에 이자 추가
                child.updateSavingAmount(monthlyInterest);

                // 부모 돈 변경
                child.getParent().updateBalance(-monthlyInterest);

                // 저축 내역에 추가한다.
                SavingHistory savingHistory = SavingHistory.builder()
                        .savingAmount(monthlyInterest)
                        .child(child)
                        .parent(child.getParent())
                        .savingDate(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli())
                        .savingType("INTEREST")
                        .build();

                savingRepository.save(savingHistory);
            }
        }
    }
}

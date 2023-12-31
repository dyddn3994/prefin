package com.prefin.service.money;

import com.prefin.domain.money.AccountHistory;
import com.prefin.domain.money.Allowance;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import com.prefin.dto.money.AllowanceDto;
import com.prefin.repository.money.AccountHistoryRepository;
import com.prefin.repository.money.AllowanceRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.repository.user.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AllowanceService {

    private final AllowanceRepository allowanceRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final AccountHistoryRepository accountHistoryRepository;

    // 정기 용돈 설정 및 수정
    @Transactional
    public ResponseEntity<String> allowanceSetting(AllowanceDto allowanceDto) {
        // 만약 설정된 정기 용돈 중 중복된 경우가 있다면 수정 로직으로
        if (allowanceRepository.findByParentIdAndChildId(
                allowanceDto.getParentId(), allowanceDto.getChildId()) != null) {
            Allowance allowance = allowanceRepository.findByParentIdAndChildId(
                    allowanceDto.getParentId(), allowanceDto.getChildId());

            allowance.updateAllowance(allowanceDto.getAllowanceAmount(), allowanceDto.getPayday());

            return ResponseEntity.status(HttpStatus.OK).body("용돈 수정 완료");
        }

        // 부모와 자식의 정보를 가져온다
        Parents parent = parentRepository.findById(allowanceDto.getParentId())
                .orElseThrow(() -> new EntityNotFoundException("Parent Not Found"));

        Child child = childRepository.findById(allowanceDto.getChildId())
                .orElseThrow(() -> new EntityNotFoundException("Child Not Found"));

        // 용돈 테이블에 새로운 용돈을 추가
        // 위에서 가져온 부모, 자식의 정보와 Dto에서 받은 용돈 금액, 용돈 지급일을 입력한다.
        Allowance newAllowance = Allowance.builder()
                .parent(parent)
                .child(child)
                .allowanceAmount(allowanceDto.getAllowanceAmount())
                .payday(allowanceDto.getPayday())
                .build();

        allowanceRepository.save(newAllowance);

        return ResponseEntity.ok("용돈 설정 완료");
    }
//
//    // 정기 용돈 수정
//    public ResponseEntity<String> allowanceUpdate(AllowanceDto allowanceDto) {
//        Allowance allowance = allowanceRepository.findByParentIdAndChildId(
//                allowanceDto.getParentId(), allowanceDto.getChildId());
//
//        allowance.updateAllowance(allowanceDto.getAllowanceAmount(), allowanceDto.getPayday());
//
//        return ResponseEntity.status(HttpStatus.OK).body("용돈 수정 완료");
//    }

    // 정기 용돈 삭제
    @Transactional
    public ResponseEntity<String> deleteAutoTransfer(Long allowanceId) {
        Allowance allowance = allowanceRepository.findById(allowanceId).orElse(null);
        allowanceRepository.delete(allowance);

        return ResponseEntity.ok("삭제 완료");
    }

    // 용돈 수동 이체 로직
    @Transactional
    public ResponseEntity<String> allowanceTransfer(AllowanceDto requestDto, String type) {  // AllowanceDto 로 받고
        // 어떤 부모가 어떤 자식에게 얼마를 보내는 지만 알면 된다.
        int immediateAllowance = requestDto.getAllowanceAmount();

        Parents parent = parentRepository.findById(requestDto.getParentId())
                .orElseThrow(() -> new EntityNotFoundException("Parent Not Found"));
        Child child = childRepository.findById(requestDto.getChildId())
                .orElseThrow(() -> new EntityNotFoundException("Child Not Found"));

        if (parent.getBalance() >= immediateAllowance) {
            child.addMoney(immediateAllowance);
            parent.transfer(immediateAllowance);

            if (type.equals("ALLOWANCE")) {
                // 거래 내역 추가
                LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

                String formattedDateTime = now.format(dateTimeFormatter);
                String formattedTime = now.format(timeFormatter);

                AccountHistory accountHistory = AccountHistory.builder().
                        child(child).
                        transactionDate(formattedDateTime).
                        transactionTime(formattedTime).
                        briefs("용돈").
                        deposit(String.valueOf(requestDto.getAllowanceAmount())).
                        withdraw("0").
                        build();

                accountHistoryRepository.save(accountHistory);
            }

            return ResponseEntity.ok("일반 용돈 지급 완료");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("계좌 잔액이 부족합니다.");
        }
    }

    // 부모 잔액 조회
    public ResponseEntity<Integer> parentBalance(Long parentId) {
        Parents parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent Not Found"));

        return ResponseEntity.ok(parent.getBalance());
    }

    // 자녀 잔액 조회
    public ResponseEntity<Integer> childBalance(Long childId) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new IllegalArgumentException("Parent Not Found"));

        return ResponseEntity.ok(child.getBalance());
    }
}

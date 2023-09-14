package com.prefin.service.money;

import com.prefin.domain.money.Allowance;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import com.prefin.dto.money.AllowanceDto;
import com.prefin.repository.money.AllowanceRepository;
import com.prefin.repository.money.LoanRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.repository.user.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AllowanceService {

    private final AllowanceRepository allowanceRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;


    // 용돈을 얼마 줄지, 언제 줄지 설정
    @Transactional
    public ResponseEntity<String> allowanceSetting(AllowanceDto allowanceDto) {
        // 부모와 자식의 정보를 가져온다
        Parents parent = null;
        Child child = null;

        if (allowanceDto.getParentId() != null) {
            parent = parentRepository.findById(allowanceDto.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent Not Found"));
        }
        if (allowanceDto.getChildId() != null) {
            child = childRepository.findById(allowanceDto.getChildId())
                    .orElseThrow(() -> new EntityNotFoundException("Child Not Found"));
        }
//        Long payday = allowanceDto.getPayday();
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


    // 용돈 수동 이체 로직
    @Transactional
    public ResponseEntity<String> allowanceTransfer(AllowanceDto requestDto) {  // AllowanceDto 로 받고
        // 어떤 부모가 어떤 자식에게 얼마를 보내는 지만 알면 된다.
        int immediateAllowance = requestDto.getAllowanceAmount();

        Parents parent = parentRepository.findById(requestDto.getParentId())
                .orElseThrow(() -> new EntityNotFoundException("Parent Not Found"));
        Child child = childRepository.findById(requestDto.getChildId())
                .orElseThrow(() -> new EntityNotFoundException("Child Not Found"));

        if (parent.getBalance() >= immediateAllowance) {
            child.addMoney(immediateAllowance);
            parent.transfer(immediateAllowance);
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

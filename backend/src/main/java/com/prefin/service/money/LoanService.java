package com.prefin.service.money;

import com.prefin.domain.money.LoanHistory;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import com.prefin.dto.money.LoanDto;
import com.prefin.repository.money.LoanRepository;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;

//    @Transactional
//    public ResponseEntity<String> borrowMoney(LoanDto loanDto) {
//        // 대출 내역에 데이터를 추가하는 느낌
//        // 부모와 자식의 정보를 가져온다
//        // 대출 하는 로직
//
//        Parents parent = null;
//        Child child = null;
//
//        if (loanDto.getParentId() != null) {
//            parent = parentRepository.findById(loanDto.getParentId())
//                    .orElseThrow(() -> new EntityNotFoundException("Parent Not Found"));
//        }
//        if (loanDto.getChildId() != null) {
//            child = childRepository.findById(loanDto.getChildId())
//                    .orElseThrow(() -> new EntityNotFoundException("Child Not Found"));
//        }
//
//        int loanMoney = loanDto.getLoanAmount();
//        int parentBalance = parent.getBalance();
//
//        // 잔액과 대츨금을 비교한다. 잔액 부족시 BAD_REQUEST를 실행
//        if (loanMoney > parentBalance) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("부모 계좌의 잔액이 부족합니다.");
//        }
//
//        parent.transfer(loanMoney);  // 부모 계좌에서는 빌린 만큼 돈이 빠지고
//        child.addMoney(loanMoney);  // 자식 계좌에서는 빌린 만큼 돈이 늘어난다.
//        child.addLoan(loanMoney); // 빌린 금액만큼 자식에게서 loanAmount를 늘린다
//
//        // 이렇게 가져온 정보를 dto를 통해 LoanHistory에 넣는다.
//        LoanHistory loanHistory = LoanHistory.builder()
//                .loanAmount(loanDto.getLoanAmount())
//                .loanDate(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli())
//                .parent(parent)
//                .child(child)
//                .build();
//
//        loanRepository.save(loanHistory);
//        return ResponseEntity.ok("대출 완료");
//    }

    @Transactional
    public ResponseEntity<String> askForMoney(LoanDto loanDto) {  // 대출 신청 기능
        // 부모와 자식의 정보를 가져온다
        // 대출 테이블에 추가하는 느낌

        Parents parent = null;
        Child child = null;

        if (loanDto.getParentId() != null) {
            parent = parentRepository.findById(loanDto.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent Not Found"));
        }
        if (loanDto.getChildId() != null) {
            child = childRepository.findById(loanDto.getChildId())
                    .orElseThrow(() -> new EntityNotFoundException("Child Not Found"));
        }

        // 이렇게 가져온 정보를 dto를 통해 LoanHistory에 넣는다.
        LoanHistory loanHistory = LoanHistory.builder()
                .loanAmount(loanDto.getLoanAmount())
                .isAccepted(false)
                .parent(parent)
                .child(child)
                .build();

        loanRepository.save(loanHistory);
        return ResponseEntity.ok("대출 신청 완료");
    }

    @Transactional
    public ResponseEntity<String> giveMoney(LoanDto loanDto) {  // 대출 확인 후 지급
        // 대출 내역 id로 어떤 대출인지 확인한다.
        // 대출 해주는 로직

        Parents parent = null;
        Child child = null;

        LoanHistory myLoan = loanRepository.findById(loanDto.getLoanId()).orElse(null);

        if (myLoan.getParent().getId() != null) {
            parent = parentRepository.findById(myLoan.getParent().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent Not Found"));
        }
        if (myLoan.getChild().getId() != null) {
            child = childRepository.findById(myLoan.getChild().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Child Not Found"));
        }

        int loanMoney = myLoan.getLoanAmount();
        int parentBalance = parent.getBalance();

        // 잔액과 대츨금을 비교한다. 잔액 부족시 BAD_REQUEST를 실행
        if (loanMoney > parentBalance) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("부모 계좌의 잔액이 부족합니다.");
        }

        parent.transfer(loanMoney);  // 부모 계좌에서는 빌린 만큼 돈이 빠지고
        child.addMoney(loanMoney);  // 자식 계좌에서는 빌린 만큼 돈이 늘어난다.
        child.addLoan(loanMoney); // 빌린 금액만큼 자식에게서 loanAmount를 늘린다

        // 이렇게 가져온 정보를 업데이트 해준다. loanDate와 isAccepted 두 가지
        Long today = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        myLoan.lendMoney(today);

        return ResponseEntity.ok("대출 완료");
    }
}

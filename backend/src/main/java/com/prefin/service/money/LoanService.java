package com.prefin.service.money;

import com.prefin.domain.money.LoanHistory;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import com.prefin.dto.money.LoanDto;
import com.prefin.repository.money.LoanRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.repository.user.ParentRepository;
import com.prefin.service.firebase.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    private boolean isMaxLoan(LoanDto loanDto, Child child, int loanWaiting) {
        BigDecimal maxLoanRate = BigDecimal.valueOf(child.getTrustScore() * (1.0 / 20));
        BigDecimal allowance = BigDecimal.valueOf(child.getAllowance().getAllowanceAmount());
        BigDecimal tempMaxLoan = maxLoanRate.multiply(allowance).multiply(new  BigDecimal("0.01"));
        int maxLoan = tempMaxLoan.setScale(0, RoundingMode.FLOOR).intValue();

        if (maxLoan < loanWaiting + loanDto.getLoanAmount() + child.getLoanAmount()) {
            return true;
        }
        return false;
    }

    // 대출 신청
    @Transactional
    public ResponseEntity<String> askForMoney(LoanDto loanDto) throws IOException {
        // 부모와 자식의 정보를 가져온다
        // 대출 테이블에 추가하는 느낌
        Parents parent = parentRepository.findById(loanDto.getParentId())
                .orElseThrow(() -> new EntityNotFoundException("Parent Not Found"));

        Child child = childRepository.findById(loanDto.getChildId())
                .orElseThrow(() -> new EntityNotFoundException("Child Not Found"));

        int loanWaiting = 0;

        List<LoanHistory> loanHistoryList = loanRepository.findAllByChildId(child.getId());
        for (LoanHistory loanAsked:loanHistoryList){
            if (!loanAsked.getIsAccepted()) {
                loanWaiting += loanAsked.getLoanAmount();
            }
        }

        if (isMaxLoan(loanDto, child, loanWaiting))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("최대 대출 금액을 벗어납니다.");

        // 이렇게 가져온 정보를 dto를 통해 LoanHistory에 넣는다.
        LoanHistory loanHistory = LoanHistory.builder()
                .loanAmount(loanDto.getLoanAmount())
                .isAccepted(false)
                .parent(parent)
                .child(child)
                .build();

        loanRepository.save(loanHistory);

        // FCM
        String token = child.getParent().getFcmToken();
        String title = "대출";
        String body = child.getName() + "님으로 부터 " + loanDto.getLoanAmount() + "원 대출 신청 되었습니다.";
        firebaseCloudMessageService.sendMessageTo(token, title, body);

        return ResponseEntity.ok("대출 신청 완료");
    }

    // 대출 신청 수정
    @Transactional
    public ResponseEntity<String> updateLoanRequest(LoanDto loanDto) {
        LoanHistory loanRequest = loanRepository.findById(loanDto.getLoanId())
                .orElseThrow(() -> new EntityNotFoundException("LoanHistory Not Found") );

        Child child = childRepository.findById(loanRequest.getChild().getId())
                .orElseThrow(() -> new EntityNotFoundException("Child Not Found"));

        if (loanRequest.getIsAccepted()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이미 처리된 대출 신청입니다.");
        }

        int loanWaiting = 0;

        List<LoanHistory> loanHistoryList = loanRepository.findAllByChildId(child.getId());
        for (LoanHistory loanAsked:loanHistoryList){
            if (!Objects.equals(loanAsked.getId(), loanRequest.getId()) && !loanAsked.getIsAccepted()) {
                loanWaiting += loanAsked.getLoanAmount();
            }
        }

        if (isMaxLoan(loanDto, child, loanWaiting))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("최대 대출 금액을 벗어납니다.");

        loanRequest.updateLoanRequest(loanDto.getLoanAmount());

        return ResponseEntity.ok("대출 수정 완료");
    }

    // 대출 신청 삭제
    @Transactional
    public ResponseEntity<String> deleteRequest(Long loanId) {
        LoanHistory loanRequest = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("LoanHistory Not Found") );

        if (loanRequest.getIsAccepted()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이미 처리된 대출 신청입니다.");
        }

        loanRepository.delete(loanRequest);
        return ResponseEntity.ok("대출 신청 취소");
    }

    // 대출 지급
    @Transactional
    public ResponseEntity<String> giveMoney(LoanDto loanDto) throws IOException {  // 대출 확인 후 지급
        // 대출 내역 id로 어떤 대출인지 확인한다.
        // 대출 해주는 로직
        LoanHistory myLoan = loanRepository.findById(loanDto.getLoanId()).orElse(null);

        Parents parent = parentRepository.findById(myLoan.getParent().getId())
                .orElseThrow(() -> new EntityNotFoundException("Parent Not Found"));

        Child child = childRepository.findById(myLoan.getChild().getId())
                .orElseThrow(() -> new EntityNotFoundException("Child Not Found"));

        if (child.getTrustScore() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("신뢰 점수가 0점 입니다. 대출이 불가합니다.");
        }

        int loanMoney = myLoan.getLoanAmount();
        int parentBalance = parent.getBalance();

        // 잔액과 대츨금을 비교한다. 잔액 부족시 BAD_REQUEST를 실행
        if (loanMoney > parentBalance) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("부모 계좌의 잔액이 부족합니다.");
        }

        parent.transfer(loanMoney);  // 부모 계좌에서는 빌린 만큼 돈이 빠지고
        child.addMoney(loanMoney);  // 자식 계좌에서는 빌린 만큼 돈이 늘어난다.
        child.updateTrustScore(-10);  // 그리고 신뢰점수 -10
        child.addLoan(loanMoney); // 빌린 금액만큼 자식에게서 loanAmount를 늘린다

        // 이렇게 가져온 정보를 업데이트 해준다. loanDate와 isAccepted 두 가지
        Long today = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        myLoan.lendMoney(today);

        // FCM
        String token = child.getFcmToken();
        String title = "대출";
        String body = loanMoney + "원 대출이 완료 되었습니다.";
        firebaseCloudMessageService.sendMessageTo(token, title, body);

        return ResponseEntity.ok("대출 완료");
    }

    // 대출 내역 조회
    public List<LoanDto> loanHistory(Long childId) {
        List<LoanHistory> loanHistoryList = loanRepository.findAllByChildId(childId);

        List<LoanDto> loanDtos = loanHistoryList.stream()
                .map(LoanDto::fromEntity)
                .collect(Collectors.toList());

        return loanDtos;
    }
}

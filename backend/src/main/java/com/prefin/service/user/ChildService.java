package com.prefin.service.user;

import com.prefin.domain.money.SavingHistory;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import com.prefin.dto.user.ChildDto;
import com.prefin.dto.user.ParentDto;
import com.prefin.repository.money.SavingRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.repository.user.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Transactional
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;
    private final SavingRepository savingRepository;

    // 자녀 회원 가입
    public Long signUp(ChildDto child) {

        Child newChild = Child.builder().
                userId(child.getUserId()).
                password(child.getPassword()).
                name(child.getName()).
                parent(parentRepository.findById(child.getParentId()).orElse(null)).
                isQuizSolved(false).
                quizId(1L).
                build();

        return childRepository.save(newChild).getId();
    }

    // 로그인
    public ChildDto login(String userId, String password) {
        // id로 회원 정보 찾기
        Child child = childRepository.findByUserId(userId).orElse(null);

        if (child == null) return null;

        // 비밀번호 일치여부 확인
        if (child.getPassword().equals(password)) {
            return ChildDto.fromEntity(child);
        }

        return null;
    }

    // 계좌 등록
    public ResponseEntity<Boolean> setAccount(long id, String account) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.updateAccount(account);
        childRepository.save(child);

        return ResponseEntity.ok().body(true);
    }

    // 간편 비밀번호 등록
    public ResponseEntity<Boolean> setSimplePassword(long id, String simplePassword) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.updateSimplePass(simplePassword);
        childRepository.save(child);

        return ResponseEntity.ok().body(true);
    }

    // 저축 금액 변경
    public ResponseEntity<Boolean> changeSavingAmount(long id, int savingAmount) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.updateSavingAmount(savingAmount);
        childRepository.save(child);

        return ResponseEntity.ok().body(true);
    }

    // 신뢰 점수 변경
    public ResponseEntity<Boolean> changeTrustScore(long id, int score) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.updateTrustScore(score);
        childRepository.save(child);

        return ResponseEntity.ok().body(true);
    }

    // 부모 설정
    public ResponseEntity<Boolean> setParent(long childId, long parentId) {
        Child child = childRepository.findById(childId).orElse(null);
        Parents parent = parentRepository.findById(parentId).orElse(null);

        if (child == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        if (parent == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.updateParent(parent);
        childRepository.save(child);

        return ResponseEntity.ok().body(true);
    }


    // 퀴즈풀이를 true로 변경
    public ResponseEntity<Boolean> solveQuiz(long id) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        child.quizSolved();
        childRepository.save(child);

        return ResponseEntity.ok().body(true);
    }

    // 다음날이 지나면 퀴즈 번호를 증가시키고 퀴즈 풀이를 false로 변경
    public ResponseEntity<Boolean> increaseQuizId(long id) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.increaseQuizId();
        childRepository.save(child);

        return ResponseEntity.ok().body(true);
    }

    // FCM Token 등록
    public ResponseEntity<Boolean> setToken(long id, String fcmToken) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.updateToken(fcmToken);
        childRepository.save(child);
        return ResponseEntity.ok().body(true);
    }

    // 저축하거나 출금할 때 가능여부를 체크하는 로직이 필요함.
    // 저축 하기
    public ResponseEntity<Boolean> deposit(long id, int balance) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        if (child.getBalance() - balance < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

        child.getParent().updateBalance(balance);
        child.updateBalance(-balance);
        child.updateSavingAmount(balance);

        SavingHistory savingHistory = SavingHistory.builder().
                savingAmount(balance).
                savingDate(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()).
                parent(child.getParent()).
                child(child).
                savingType("DEPOSIT").
                build();

        savingRepository.save(savingHistory);
        childRepository.save(child);

        return ResponseEntity.ok(true);
    }

    // 출금 하기
    public ResponseEntity<Boolean> withdraw(long id, int balance) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        if (child.getParent().getBalance() - balance < 0) ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        child.getParent().updateBalance(-balance);
        child.updateBalance(balance);
        child.updateSavingAmount(-balance);

        SavingHistory savingHistory = SavingHistory.builder().
                savingAmount(balance).
                savingDate(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()).
                parent(child.getParent()).
                child(child).
                savingType("WITHDRAW").
                build();

        savingRepository.save(savingHistory);
        childRepository.save(child);

        return ResponseEntity.ok(true);
    }

    public ChildDto getChildById(Long id) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return null;

        return ChildDto.fromEntity(child);
    }
}

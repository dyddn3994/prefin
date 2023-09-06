package com.prefin.service.user;

import com.prefin.domain.entertainment.Mascot;
import com.prefin.domain.money.SavingHistory;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parent;
import com.prefin.dto.user.ChildDto;
import com.prefin.repository.entertainment.MascotRepository;
import com.prefin.repository.money.SavingRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.repository.user.ParentRepository;
import lombok.RequiredArgsConstructor;
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
    private final MascotRepository mascotRepository;
    private final SavingRepository savingRepository;

    // 자녀 회원 가입
    public Long signUp(ChildDto child) {

        Child newChild = Child.builder().
                userId(child.getUserId()).
                password(child.getPassword()).
                name(child.getName()).
                isQuizSolved(false).
                quizId(0L).
                build();

        return childRepository.save(newChild).getId();
    }

    // 로그인
    public Long login(String userId, String password) {
        // id로 회원 정보 찾기
        Child child = childRepository.findByUserId(userId).orElse(null);

        if (child == null) return -1L;

        // 비밀번호 일치여부 확인
        if (child.getPassword().equals(password)) {
            return child.getId();
        }

        return -1L;
    }

    // 계좌 등록
    public Long setAccount(long id, String account) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return -1L;

        child.updateAccount(account);

        return childRepository.save(child).getId();
    }

    // 간편 비밀번호 등록
    public Long setSimplePassword(long id, String simplePassword) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return -1L;

        child.updateSimplePass(simplePassword);

        return childRepository.save(child).getId();
    }

    // 저축 금액 변경
    public void changeSavingAccount(long id, int savingAccount) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return;

        child.updateSavingAccount(savingAccount);

        childRepository.save(child);
    }

    // 신뢰 점수 변경
    public void changeTrustScore(long id, int score) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return;

        child.updateTrustScore(score);

        childRepository.save(child);
    }

    // 부모 설정
    public void setParent(long childId, long parentId) {
        Child child = childRepository.findById(childId).orElse(null);
        Parent parent = parentRepository.findById(parentId).orElse(null);

        child.updateParent(parent);

        childRepository.save(child);
    }

    // 마스코트 설정
    public void updateMascot(long childId, long mascotId) {
        Child child = childRepository.findById(childId).orElse(null);
        Mascot mascot = mascotRepository.findById(mascotId).orElse(null);

        child.updateMascot(mascot);

        childRepository.save(child);
    }

    // 퀴즈풀이를 true로 변경
    public void solveQuiz(long id) {
        Child child = childRepository.findById(id).orElse(null);

        child.quizSolved();

        childRepository.save(child);
    }

    // 다음날이 지나면 퀴즈 번호를 증가시키고 퀴즈 풀이를 false로 변경
    public void increaseQuizId(long id) {
        Child child = childRepository.findById(id).orElse(null);

        child.increaseQuizId();

        childRepository.save(child);
    }

    // FCM Token 등록
    public Long setToken(long id, String fcmToken) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return -1L;

        child.updateToken(fcmToken);

        return childRepository.save(child).getId();
    }

    // 저축하거나 출금할 때 가능여부를 체크하는 로직이 필요함.
    // 저축 하기
    public void deposit(long id, int balance) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return;

        child.getParent().updateBalance(balance);
        child.updateBalance(-balance);
        child.updateSavingAccount(balance);

        SavingHistory savingHistory = SavingHistory.builder().
                savingAmount(balance).
                savingDate(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()).
                parent(child.getParent()).
                child(child).
                savingType("DEPOSIT").
                build();

        savingRepository.save(savingHistory);
        childRepository.save(child);
    }

    // 출금 하기
    public void withdraw(long id, int balance) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return;

        child.getParent().updateBalance(-balance);
        child.updateBalance(balance);
        child.updateSavingAccount(-balance);

        SavingHistory savingHistory = SavingHistory.builder().
                savingAmount(balance).
                savingDate(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()).
                parent(child.getParent()).
                child(child).
                savingType("WITHDRAW").
                build();

        savingRepository.save(savingHistory);
        childRepository.save(child);
    }
}

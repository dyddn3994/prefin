package com.prefin.service.user;

import com.prefin.domain.entertainment.Mascot;
import com.prefin.domain.money.SavingHistory;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
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
                parent(parentRepository.findById(child.getParentId()).orElse(null)).
                isQuizSolved(false).
                quizId(1L).
                build();

        return childRepository.save(newChild).getId();
    }

    // 로그인
    public Child login(String userId, String password) {
        // id로 회원 정보 찾기
        Child child = childRepository.findByUserId(userId).orElse(null);

        if (child == null) return null;

        // 비밀번호 일치여부 확인
        if (child.getPassword().equals(password)) {
            return child;
        }

        return null;
    }

    // 계좌 등록
    public String setAccount(long id, String account) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return "child not exist";

        child.updateAccount(account);
        childRepository.save(child);

        return "set Account: " + account;
    }

    // 간편 비밀번호 등록
    public String setSimplePassword(long id, String simplePassword) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return "child not exist";

        child.updateSimplePass(simplePassword);
        childRepository.save(child);

        return "set SimplePassword: " + simplePassword;
    }

    // 저축 금액 변경
    public String changeSavingAmount(long id, int savingAmount) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return "child not exist";

        child.updateSavingAmount(savingAmount);
        childRepository.save(child);

        return "change SavingAmount: " + savingAmount;
    }

    // 신뢰 점수 변경
    public String changeTrustScore(long id, int score) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return "child not exist";

        child.updateTrustScore(score);
        childRepository.save(child);

        return "change TrustScore: " + score;
    }

    // 부모 설정
    public String setParent(long childId, long parentId) {
        Child child = childRepository.findById(childId).orElse(null);
        Parents parent = parentRepository.findById(parentId).orElse(null);

        if (child == null) return "child not exist";
        if (parent == null) return "parent not exist";

        child.updateParent(parent);
        childRepository.save(child);

        return "set Parent: " + parent.getName();
    }

    // 마스코트 설정
    public String updateMascot(long childId, long mascotId) {
        Child child = childRepository.findById(childId).orElse(null);
        Mascot mascot = mascotRepository.findById(mascotId).orElse(null);

        if (child == null) return "child not exist";
        if (mascot == null) return "mascot not exist";

        child.updateMascot(mascot);
        childRepository.save(child);

        return "change Mascot: " + mascot.getName();
    }

    // 퀴즈풀이를 true로 변경
    public String solveQuiz(long id) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return "child not exist";

        child.quizSolved();
        childRepository.save(child);

        return "Today Quiz Solved";
    }

    // 다음날이 지나면 퀴즈 번호를 증가시키고 퀴즈 풀이를 false로 변경
    public String increaseQuizId(long id) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return "child not exist";

        child.increaseQuizId();
        childRepository.save(child);

        return "Quiz Updated";
    }

    // FCM Token 등록
    public String setToken(long id, String fcmToken) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return "child not exist";

        child.updateToken(fcmToken);
        childRepository.save(child);
        return "set Token: " + fcmToken;
    }

    // 저축하거나 출금할 때 가능여부를 체크하는 로직이 필요함.
    // 저축 하기
    public void deposit(long id, int balance) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return;

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
    }

    // 출금 하기
    public void withdraw(long id, int balance) {
        Child child = childRepository.findById(id).orElse(null);

        if (child == null) return;

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
    }
}

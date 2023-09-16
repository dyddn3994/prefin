package com.prefin.component;

import com.prefin.domain.user.Child;
import com.prefin.repository.user.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuizIdIncrease {

    private final ChildRepository childRepository;

    // 퀴즈 확인
    // 매 정각마다 모든 자녀 목록 들고와서 quizSolved가 true인 자녀들은 퀴즈 번호를 증가시키고  quizSolved를 다시 false로 바꾼다
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")  // "0 * * * * ?"
    public void autoQuizChecker() {
        List<Child> childList = childRepository.findAll();
        for (Child child : childList) {
            if (child.getIsQuizSolved()) {
                child.increaseQuizId();
            }
        }
    }

}

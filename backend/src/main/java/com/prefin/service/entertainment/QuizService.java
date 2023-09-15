package com.prefin.service.entertainment;

import com.prefin.domain.entertainment.Quiz;
import com.prefin.domain.user.Child;
import com.prefin.dto.entertainment.QuizDto;
import com.prefin.repository.entertainment.QuizRepository;
import com.prefin.repository.user.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final ChildRepository childRepository;

    @Transactional  // 아이쪽에 등록 되어있는 오늘의 퀴즈를 가져오는 작업
    public ResponseEntity<QuizDto> todayQuiz(Long childId) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new EntityNotFoundException("Child Not Found"));

        Quiz todayQuiz = quizRepository.findById(child.getQuizId())
                .orElseThrow(() -> new EntityNotFoundException("Quiz Not Found"));

        if (child.getIsQuizSolved()) {  // 문제를 풀었다면 설명만 주고
            QuizDto responseDto = QuizDto.builder()
                    .id(todayQuiz.getId())
                    .description(todayQuiz.getDescription())
                    .question(todayQuiz.getQuestion())
                    .build();

            return ResponseEntity.ok(responseDto);
        } else {  // 안 풀었다면 문제와 정답, 설명 정보를 모두 전송
            QuizDto responseDto = QuizDto.builder()
                    .id(todayQuiz.getId())
                    .question(todayQuiz.getQuestion())
                    .answer(todayQuiz.getAnswer())
                    .description(todayQuiz.getDescription())
                    .build();

            return ResponseEntity.ok(responseDto);
        }
    }

    @Transactional  // 문제를 맞췄는지 안 맞췄는지 확인하는 로직
    public ResponseEntity<Boolean> checkAnswer(QuizDto quizDto, Long childId) {

        Child child = childRepository.findById(childId).orElse(null);
        Quiz quiz = quizRepository.findById(child.getQuizId()).orElse(null);

        int myAnswer = quizDto.getAnswer();  // 내가 입력한 퀴즈 정답과
        int answer = quiz.getAnswer();  // 실제 정답이 일치하는지 확인

        if (answer == myAnswer) {
            // 문제 풀었다고 표시
            child.quizSolved();
            int score = 3;
            child.updateTrustScore(score);
            return  ResponseEntity.ok(true);
        } else {
            // 문제 풀었다고 표시
            child.quizSolved();
            int score = 1;
            child.updateTrustScore(score);
            return  ResponseEntity.ok(false);
        }
    }

}

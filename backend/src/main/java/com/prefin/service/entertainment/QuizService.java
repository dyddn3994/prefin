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

        QuizDto responseDto = QuizDto.builder()
                .question(todayQuiz.getQuestion())
                .answer(todayQuiz.getAnswer())
                .description(todayQuiz.getDescription())
                .build();

        return ResponseEntity.ok(responseDto);
    }
}

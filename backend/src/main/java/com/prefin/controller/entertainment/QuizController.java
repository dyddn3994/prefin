package com.prefin.controller.entertainment;

import com.prefin.dto.entertainment.QuizDto;
import com.prefin.dto.money.AllowanceDto;
import com.prefin.repository.entertainment.QuizRepository;
import com.prefin.service.entertainment.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class QuizController {
    private final QuizService quizService;

    @GetMapping("/quiz/today/{childId}")
    public ResponseEntity<QuizDto> todayQuiz(@PathVariable Long childId) {
        return quizService.todayQuiz(childId);
    }

    @PostMapping("/quiz/isCorrect/{childId}")
    public ResponseEntity<String> isCorrect(@RequestBody QuizDto requestDto, @PathVariable Long childId) {
        return quizService.checkAnswer(requestDto, childId);
    }
}

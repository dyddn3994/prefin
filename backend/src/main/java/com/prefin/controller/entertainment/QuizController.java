package com.prefin.controller.entertainment;

import com.prefin.repository.entertainment.QuizRepository;
import com.prefin.service.entertainment.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;
}

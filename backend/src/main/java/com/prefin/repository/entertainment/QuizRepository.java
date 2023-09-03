package com.prefin.repository.entertainment;

import com.prefin.domain.entertainment.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}

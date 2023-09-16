package com.prefin.dto.entertainment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {

    private Long id;

    private boolean answer;
    private String question;
    private String description;
}

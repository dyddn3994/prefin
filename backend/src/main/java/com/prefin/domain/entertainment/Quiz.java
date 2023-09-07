package com.prefin.domain.entertainment;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Quiz {
    @Id @GeneratedValue
    private Long id;

    private String question;

    private int answer;

    private String description;
}

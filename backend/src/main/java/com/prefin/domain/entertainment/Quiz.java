package com.prefin.domain.entertainment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Quiz {
    @Id @GeneratedValue
    private Long id;

    private String question;

    private int answer;

    private String description;
}

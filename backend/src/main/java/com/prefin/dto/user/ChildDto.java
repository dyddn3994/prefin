package com.prefin.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChildDto {

    private Long id;

    private String userId;

    private String password;

    private String name;

    private String fcmToken;

    private String account;

    private String simplePass;

    private Boolean isQuizSolved;

    private Long quizId;

    private int trustScore;

    private int savingAmount;

    private int balance;

    private long parentId;

    private long mascotId;
}

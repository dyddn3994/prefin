package com.prefin.dto.user;

import com.prefin.domain.user.Child;
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

    public static ChildDto fromEntity(Child child) {
        return ChildDto.builder()
                .id(child.getId())
                .userId(child.getUserId())
                .password(child.getPassword())
                .name(child.getName())
                .account(child.getAccount())
                .simplePass(child.getSimplePass())
                .isQuizSolved(child.getIsQuizSolved())
                .quizId(child.getQuizId())
                .trustScore(child.getTrustScore())
                .savingAmount(child.getSavingAmount())
                .balance(child.getBalance())
                .parentId(child.getParent().getId())
                .mascotId(child.getMascot().getId())
                .build();
    }
}

package com.prefin.dto.user;

import com.prefin.domain.money.Allowance;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    private long loanAmount;

    private int possibleLoanAmount;

    // 용돈 관련
    private int allowanceAmount;

    private long payday;

    private BigDecimal savingRate;

    private BigDecimal loanRate;

    public static ChildDto fromEntity(Child child) {
        Allowance allowance = child.getAllowance();

        if (allowance == null)
            allowance = Allowance.builder().build();

        Parents parent = child.getParent();

        if (parent == null)
            parent = Parents.builder().build();

        return ChildDto.builder()
                .id(child.getId())
                .userId(child.getUserId())
                .password(child.getPassword())
                .name(child.getName())
                .fcmToken(child.getFcmToken())
                .account(child.getAccount())
                .simplePass(child.getSimplePass())
                .isQuizSolved(child.getIsQuizSolved())
                .quizId(child.getQuizId())
                .trustScore(child.getTrustScore())
                .savingAmount(child.getSavingAmount())
                .balance(child.getBalance())
                .parentId(child.getParent().getId())
                .allowanceAmount(allowance.getAllowanceAmount())
                .payday(allowance.getPayday())
                .savingRate(child.getSavingRate())
                .loanRate(child.getLoanRate())
                .possibleLoanAmount((int) (allowance.getAllowanceAmount() * child.getTrustScore() * 0.05 * 0.01))
                .loanAmount(child.getLoanAmount())
                .build();
    }
}

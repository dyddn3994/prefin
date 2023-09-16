package com.prefin.domain.user;

import com.prefin.domain.money.Allowance;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "children")
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String password;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String name;

    private String fcmToken;

    private String account;

    private String simplePass;

    private Boolean isQuizSolved;

    private Long quizId;

    private int trustScore;

    private int savingAmount;

    private int loanAmount;

    private int balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parents parent;

    @OneToOne(mappedBy = "child")
    private Allowance allowance;

    public void updateToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void updateAccount(String account) {
        this.account = account;
    }

    public void updateSimplePass(String simplePass) {
        this.simplePass = simplePass;
    }

    public void updateTrustScore(int score) {
        this.trustScore += score;

        // 신뢰 점수 변경사항 체크
        if (trustScore >= 1000) this.getParent().updateSavingRate(BigDecimal.valueOf(2.0));
        else if (trustScore >= 900) this.getParent().updateSavingRate(BigDecimal.valueOf(1.8));
        else if (trustScore >= 800) this.getParent().updateSavingRate(BigDecimal.valueOf(1.6));
        else if (trustScore >= 700) this.getParent().updateSavingRate(BigDecimal.valueOf(1.4));
        else if (trustScore >= 600) this.getParent().updateSavingRate(BigDecimal.valueOf(1.2));
        else if (trustScore >= 500) this.getParent().updateSavingRate(BigDecimal.valueOf(1.0));
        else if (trustScore >= 400) this.getParent().updateSavingRate(BigDecimal.valueOf(0.8));
        else if (trustScore >= 300) this.getParent().updateSavingRate(BigDecimal.valueOf(0.6));
        else if (trustScore >= 200) this.getParent().updateSavingRate(BigDecimal.valueOf(0.4));
        else if (trustScore >= 100) this.getParent().updateSavingRate(BigDecimal.valueOf(0.2));
        else this.getParent().updateSavingRate(BigDecimal.valueOf(0.0));
    }

    public void updateSavingAmount(int savingAmount) {
        this.savingAmount += savingAmount;
    }

    public void updateParent(Parents parent) {
        this.parent = parent;
    }

    public void quizSolved() {
        this.isQuizSolved = true;
    }

    public void increaseQuizId() {
        this.quizId++;
        this.isQuizSolved = false;
    }

    public void updateBalance(int balance) { this.balance += balance; }

    public void addMoney(int money) {
        this.balance += money;
    }

    public void addLoan(int money) {
        this.loanAmount += money;
    }

    public void resetLoan() {
        this.loanAmount = 0;
    }

    public void subtractLoan(int money) {
        this.loanAmount -= money;
    }

}

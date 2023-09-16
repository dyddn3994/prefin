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

    private BigDecimal savingRate;

    private int loanAmount;

    private BigDecimal loanRate;

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

    public void updateLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public void updateSavingRate(BigDecimal savingRate) {
        this.savingRate = savingRate;
    }

    public void updateSimplePass(String simplePass) {
        this.simplePass = simplePass;
    }

    public void updateTrustScore(int score) {
        if (this.trustScore + score >= 1000) {
            this.trustScore = 1000;
            this.savingRate = BigDecimal.valueOf(2.0);
            this.loanRate = BigDecimal.valueOf(0.0);
            return;
        }
        this.trustScore += score;

        // 신뢰 점수 변경사항 체크
        if (this.trustScore >= 900)  {
            this.savingRate = BigDecimal.valueOf(1.8);
            this.loanRate = BigDecimal.valueOf(1.0);
        }
        else if (this.trustScore >= 800) {
            this.savingRate = BigDecimal.valueOf(1.6);
            this.loanRate = BigDecimal.valueOf(2.0);
        }
        else if (this.trustScore >= 700) {
            this.savingRate = BigDecimal.valueOf(1.4);
            this.loanRate = BigDecimal.valueOf(3.0);
        }
        else if (this.trustScore >= 600) {
            this.savingRate = BigDecimal.valueOf(1.2);
            this.loanRate = BigDecimal.valueOf(4.0);
        }
        else if (this.trustScore >= 500) {
            this.savingRate = BigDecimal.valueOf(1.0);
            this.loanRate = BigDecimal.valueOf(5.0);
        }
        else if (this.trustScore >= 400) {
            this.savingRate = BigDecimal.valueOf(0.8);
            this.loanRate = BigDecimal.valueOf(6.0);
        }
        else if (this.trustScore >= 300) {
            this.savingRate = BigDecimal.valueOf(0.6);
            this.loanRate = BigDecimal.valueOf(7.0);
        }
        else if (this.trustScore >= 200) {
            this.savingRate = BigDecimal.valueOf(0.4);
            this.loanRate = BigDecimal.valueOf(8.0);
        }
        else if (this.trustScore >= 100) {
            this.savingRate = BigDecimal.valueOf(0.2);
            this.loanRate = BigDecimal.valueOf(9.0);
        }
        else {
            this.savingRate = BigDecimal.valueOf(0.0);
            this.loanRate = BigDecimal.valueOf(10.0);
        }
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

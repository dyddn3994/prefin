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
            this.savingRate = BigDecimal.valueOf(0.018);
            this.loanRate = BigDecimal.valueOf(0.01);
        }
        else if (this.trustScore >= 800) {
            this.savingRate = BigDecimal.valueOf(0.016);
            this.loanRate = BigDecimal.valueOf(0.02);
        }
        else if (this.trustScore >= 700) {
            this.savingRate = BigDecimal.valueOf(0.014);
            this.loanRate = BigDecimal.valueOf(0.03);
        }
        else if (this.trustScore >= 600) {
            this.savingRate = BigDecimal.valueOf(0.012);
            this.loanRate = BigDecimal.valueOf(0.04);
        }
        else if (this.trustScore >= 500) {
            this.savingRate = BigDecimal.valueOf(0.010);
            this.loanRate = BigDecimal.valueOf(0.05);
        }
        else if (this.trustScore >= 400) {
            this.savingRate = BigDecimal.valueOf(0.008);
            this.loanRate = BigDecimal.valueOf(0.06);
        }
        else if (this.trustScore >= 300) {
            this.savingRate = BigDecimal.valueOf(0.006);
            this.loanRate = BigDecimal.valueOf(0.07);
        }
        else if (this.trustScore >= 200) {
            this.savingRate = BigDecimal.valueOf(0.004);
            this.loanRate = BigDecimal.valueOf(0.08);
        }
        else if (this.trustScore >= 100) {
            this.savingRate = BigDecimal.valueOf(0.002);
            this.loanRate = BigDecimal.valueOf(0.09);
        }
        else {
            this.savingRate = BigDecimal.valueOf(0.0);
            this.loanRate = BigDecimal.valueOf(0.1);
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

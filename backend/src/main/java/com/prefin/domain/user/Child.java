package com.prefin.domain.user;

import com.prefin.domain.entertainment.Mascot;
import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MASCOT_ID")
    private Mascot mascot;


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
    }

    public void updateSavingAmount(int savingAmount) {
        this.savingAmount += savingAmount;
    }

    public void updateParent(Parents parent) {
        this.parent = parent;
    }

    public void updateMascot(Mascot mascot) {
        this.mascot = mascot;
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

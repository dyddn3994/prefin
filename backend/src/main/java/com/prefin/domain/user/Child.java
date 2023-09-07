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
    @GeneratedValue
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

    private int savingAccount;

    private int loanAccount;

    private int balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

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

    public void updateSavingAccount(int savingAccount) {
        this.savingAccount += savingAccount;
    }

    public void updateLoanAccount(int loanAccount) { this.loanAccount += loanAccount; }

    public void updateParent(Parent parent) {
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
}

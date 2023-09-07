package com.prefin.domain.user;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "parents")
public class Parents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String password;

    private String name;

    private String fcmToken;

    private String account;

    private String simplePass;
    public void transfer(int money) {
        this.balance -= money;
    }

    private BigDecimal loanRate;

    private BigDecimal savingRate;

    private int balance;

    public void updateToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void updateAccount(String account) {
        this.account = account;
    }

    public void updateSimplePass(String simplePass) {
        this.simplePass = simplePass;
    }

    public void updateLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public void updateSavingRate(BigDecimal savingRate) {
        this.savingRate = savingRate;
    }

    public void updateBalance(int balance) { this.balance += balance; }
}

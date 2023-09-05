package com.prefin.domain.user;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "parents")
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String password;

    private String name;

    private String fcmToken;

    private String account;

    private String simplePass;

    private BigDecimal loanRate;

    private BigDecimal savingRate;

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


}

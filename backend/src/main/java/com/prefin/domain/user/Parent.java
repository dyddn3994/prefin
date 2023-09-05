package com.prefin.domain.user;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String name;

    private String fcmToken;

    private String account;

    private BigDecimal loanRate;

    private BigDecimal savingRate;

    private int balance;

    public void transfer(int money) {
        this.balance -= money;
    }

}

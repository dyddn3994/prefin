package com.prefin.domain.user;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String name;

    private String fcmToken;

    private String account;

    private String simplePass;
    public void transfer(int money) {
        this.balance -= money;
    }

    private int balance;

    private int maxSavingAmount;

    @OneToMany(mappedBy = "parent")
    private List<Child> childList = new ArrayList<>();

    public void updateToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void updateAccount(String account) {
        this.account = account;
    }

    public void updateSimplePass(String simplePass) {
        this.simplePass = simplePass;
    }

    public void updateBalance(int balance) { this.balance += balance; }

    public void updateMaxSavingAmount(int maxSavingAmount) {
        this.maxSavingAmount = maxSavingAmount;
    }
}

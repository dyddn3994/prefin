package com.prefin.domain.user;

import com.prefin.domain.entertainment.Mascot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Child {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String fcmToken;

    private String account;

    private Boolean isQuizSolved;

    private Long quizId;

    private int trustScore;

    private int savingAccount;

    private int balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MASCOT_ID")
    private Mascot mascot;

    public void getAllowance(int money) {
        this.balance += money;
    }
}

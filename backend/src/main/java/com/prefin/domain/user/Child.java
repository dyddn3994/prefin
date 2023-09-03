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
    Long id;

    String name;

    String fcmToken;

    String account;

    Boolean isQuizSolved;

    Long quizId;

    int trustScore;

    int savingAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MASCOT_ID")
    Mascot mascot;
}
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

    private String name;

    private String fcmToken;

    private String account;

    private Boolean isQuizSolved;

    private Long quizId;

    private int trustScore;

    private int savingAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MASCOT_ID")
    private Mascot mascot;

    private String userId;

    private String password;

    private String simplePass;
}

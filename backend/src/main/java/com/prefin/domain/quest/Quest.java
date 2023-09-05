package com.prefin.domain.quest;

import com.prefin.domain.user.Parent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Quest {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private int reward;

    private long startDate;

    private long endDate;

    private Boolean requestStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;
}

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
    Long id;

    String title;

    Integer reward;

    long startDate;

    long endDate;

    Boolean requestStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    Parent parent;
}

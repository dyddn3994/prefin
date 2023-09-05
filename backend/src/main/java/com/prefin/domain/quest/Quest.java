package com.prefin.domain.quest;

import com.prefin.domain.user.Parent;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Quest {
    @Id
    @GeneratedValue
    Long id;

    String title;

    int reward;

    long startDate;

    long endDate;

    Boolean requestStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    Parent parent;
}

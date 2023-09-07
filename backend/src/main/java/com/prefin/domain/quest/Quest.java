package com.prefin.domain.quest;

import com.prefin.domain.user.Parents;
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
    private Long id;

    private String title;

    private int reward;

    private long startDate;

    private long endDate;

    private Boolean requestStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parents parent;
}

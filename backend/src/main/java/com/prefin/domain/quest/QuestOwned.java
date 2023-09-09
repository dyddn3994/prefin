package com.prefin.domain.quest;

import com.prefin.domain.user.Child;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class QuestOwned {
    @Id
    @GeneratedValue
    private Long id;

    private boolean requested;

    private boolean completed;

    private long startDate;

    private long endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUEST_ID")
    private Quest quest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHILD_ID")
    private Child child;

    public void updateRequested(boolean requested) {this.requested = requested;}

    public void updateCompleted(boolean completed) {this.completed = completed;}
}

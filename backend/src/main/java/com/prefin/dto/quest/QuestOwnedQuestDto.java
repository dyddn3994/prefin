package com.prefin.dto.quest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestOwnedQuestDto {
    private Long questId;

    private boolean requested;

    private boolean completed;

    private long startDate;

    private long endDate;

    private String title;

    private int reward;
}

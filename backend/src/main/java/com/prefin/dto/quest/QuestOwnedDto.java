package com.prefin.dto.quest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestOwnedDto {
    private long id;

    private long childId;

    private long questId;

    private boolean requested;

    private boolean completed;

    private long startDate;

    private long endDate;
}

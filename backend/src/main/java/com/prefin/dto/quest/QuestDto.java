package com.prefin.dto.quest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestDto {
    private String title;

    private int reward;

    private boolean registered;

    private long parentId;
}

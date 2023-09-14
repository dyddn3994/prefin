package com.prefin.dto.quest;

import com.prefin.domain.quest.Quest;
import com.prefin.dto.user.ChildDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestDto {
    private Long id;

    private String title;

    private int reward;

    private Boolean registered;

    private Long parentId;

    public static QuestDto fromEntity(Quest quest) {
        return QuestDto.builder()
                .id(quest.getId())
                .title(quest.getTitle())
                .reward(quest.getReward())
                .registered(quest.getRegistered())
                .parentId(quest.getParent().getId())
                .build();
    }
}

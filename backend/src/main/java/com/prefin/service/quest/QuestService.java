package com.prefin.service.quest;

import com.prefin.domain.quest.Quest;
import com.prefin.dto.quest.QuestDto;
import com.prefin.repository.quest.QuestRepository;
import com.prefin.repository.user.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestService {
    private final ParentRepository parentRepository;
    private final QuestRepository questRepository;

    // 퀘스트 생성
    public String makeQuest(QuestDto questDto) {
        Quest quest = Quest.builder().
                title(questDto.getTitle()).
                reward(questDto.getReward()).
                registered(false).
                parent(parentRepository.findById(questDto.getParentId()).orElse(null)).
                build();

        questRepository.save(quest);

        return "Quest Created";
    }

    // 퀘스트 삭제
    public String deleteQuest(long id) {
        Quest quest = questRepository.findById(id).orElse(null);

        if (quest == null) return "quest not exist";

        questRepository.delete(quest);

        return "quest deleted";
    }


}

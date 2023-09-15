package com.prefin.service.quest;

import com.prefin.domain.quest.Quest;
import com.prefin.domain.user.Parents;
import com.prefin.dto.quest.QuestDto;
import com.prefin.dto.user.ParentDto;
import com.prefin.repository.quest.QuestRepository;
import com.prefin.repository.user.ParentRepository;
import kotlin.collections.ArrayDeque;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestService {
    private final ParentRepository parentRepository;
    private final QuestRepository questRepository;

    // 퀘스트 생성
    public ResponseEntity<Boolean> makeQuest(QuestDto questDto) {
        Quest quest = Quest.builder().
                title(questDto.getTitle()).
                reward(questDto.getReward()).
                registered(false).
                parent(parentRepository.findById(questDto.getParentId()).orElse(null)).
                build();

        questRepository.save(quest);

        return ResponseEntity.ok(true);
    }

    // 퀘스트 삭제
    public ResponseEntity<Boolean> deleteQuest(long id) {
        Quest quest = questRepository.findById(id).orElse(null);

        if (quest == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        questRepository.delete(quest);

        return ResponseEntity.ok(true);
    }


    public QuestDto getQuestById(long id) {
        Quest quest = questRepository.findById(id).orElse(null);

        if (quest == null) return null;

        return QuestDto.fromEntity(quest);
    }

    public List<QuestDto> findByParent(Long id) {
        Parents parent = parentRepository.findById(id).orElse(null);
        List<Quest> quests = questRepository.findByParent(parent);
        List<QuestDto> questDtos = new ArrayDeque<>();

        for (Quest quest : quests) {
            questDtos.add(QuestDto.fromEntity(quest));
        }

        return questDtos;
    }
}

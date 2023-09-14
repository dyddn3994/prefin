package com.prefin.service.quest;

import com.prefin.domain.quest.Quest;
import com.prefin.domain.quest.QuestOwned;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import com.prefin.dto.money.AllowanceDto;
import com.prefin.dto.quest.QuestOwnedDto;
import com.prefin.repository.quest.QuestOwnedRepository;
import com.prefin.repository.quest.QuestRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.repository.user.ParentRepository;
import com.prefin.service.money.AllowanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestOwnedService {
    private final QuestOwnedRepository questOwnedRepository;
    private final AllowanceService allowanceService;
    private final QuestRepository questRepository;
    private final ChildRepository childRepository;

    // 퀘스트 소유 등록
    public ResponseEntity<Boolean> makeQuestOwned(QuestOwnedDto questOwnedDto) {
        Child child = childRepository.findById(questOwnedDto.getChildId()).orElse(null);

        if (child == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        Quest quest = questRepository.findById(questOwnedDto.getQuestId()).orElse(null);

        if (quest == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        // 퀘스트 소유 생성
        QuestOwned questOwned = QuestOwned.builder().
                child(child).
                quest(quest).
                requested(false).
                completed(false).
                startDate(questOwnedDto.getStartDate()).
                endDate(questOwnedDto.getEndDate()).
                build();

        questOwnedRepository.save(questOwned);

        // 퀘스트 등록을 true로 변경
        quest.updateRegistered(true);
        questRepository.save(quest);

        return ResponseEntity.ok(true);
    }

    // 퀘스트 완료 요청 (fcm 보내기)
    public ResponseEntity<Boolean> requestQuestComplete(long id) {
        QuestOwned questOwned = questOwnedRepository.findById(id).orElse(null);

        if (questOwned == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        questOwned.updateRequested(true);
        questOwnedRepository.save(questOwned);

        return ResponseEntity.ok(true);
    }

    // 퀘스트 완료 (퀘스트의 등록 여부를 false로 업데이트)
    public ResponseEntity<Boolean> setQuestCompleted(long id) {
        QuestOwned questOwned = questOwnedRepository.findById(id).orElse(null);

        if (questOwned == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        questOwned.updateCompleted(true);
        questOwnedRepository.save(questOwned);

        Quest quest = questOwned.getQuest();
        quest.updateRegistered(false);
        questRepository.save(quest);

        // 돈 전송
        AllowanceDto allowanceDto = AllowanceDto.builder().
                allowanceAmount(quest.getReward()).
                childId(questOwned.getChild().getId()).
                parentId(quest.getParent().getId()).build();

        allowanceService.allowanceTransfer(allowanceDto);

        return ResponseEntity.ok(true);
    }

    public QuestOwnedDto findById(long id) {
        QuestOwned questOwned = questOwnedRepository.findById(id).orElse(null);

        return QuestOwnedDto.builder()
                .questId(questOwned.getId())
                .childId(questOwned.getChild().getId())
                .questId(questOwned.getQuest().getId())
                .requested(questOwned.isRequested())
                .completed(questOwned.isCompleted())
                .startDate(questOwned.getStartDate())
                .endDate(questOwned.getEndDate())
                .build();
    }

    public List<QuestOwnedDto> findByChild(Long id) {
        Child child = childRepository.findById(id).orElse(null);

        List<QuestOwned> questOwneds = questOwnedRepository.findByChild(child);

        List<QuestOwnedDto> questOwnedDtos = new ArrayList<>();

        for (QuestOwned questOwned : questOwneds) {
            questOwnedDtos.add(QuestOwnedDto.builder()
                    .id(questOwned.getId())
                    .childId(questOwned.getChild().getId())
                    .questId(questOwned.getQuest().getId())
                    .requested(questOwned.isRequested())
                    .completed(questOwned.isCompleted())
                    .startDate(questOwned.getStartDate())
                    .endDate(questOwned.getEndDate()).build());
        }

        return questOwnedDtos;
    }
}

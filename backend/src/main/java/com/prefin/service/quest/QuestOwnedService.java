package com.prefin.service.quest;

import com.prefin.domain.quest.Quest;
import com.prefin.domain.quest.QuestOwned;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import com.prefin.dto.quest.QuestOwnedDto;
import com.prefin.repository.quest.QuestOwnedRepository;
import com.prefin.repository.quest.QuestRepository;
import com.prefin.repository.user.ChildRepository;
import com.prefin.repository.user.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestOwnedService {
    private final QuestOwnedRepository questOwnedRepository;
    private final QuestRepository questRepository;
    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;

    // 퀘스트 소유 등록
    public String makeQuestOwned(QuestOwnedDto questOwnedDto) {
        Child child = childRepository.findById(questOwnedDto.getChildId()).orElse(null);

        if (child == null) return "child not exist";

        Quest quest = questRepository.findById(questOwnedDto.getQuestId()).orElse(null);

        if (quest == null) return "quest not exist";

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

        return "Quest Registered";
    }

    // 퀘스트 완료 요청 (fcm 보내기)
    public String requestQuestComplete(long id) {
        QuestOwned questOwned = questOwnedRepository.findById(id).orElse(null);

        if (questOwned == null) return "quest not exist";

        questOwned.updateRequested(true);
        questOwnedRepository.save(questOwned);

        return "quest is requested";
    }

    // 퀘스트 완료 (퀘스트의 등록 여부를 false로 업데이트)
    public String setQuestCompleted(long id) {
        QuestOwned questOwned = questOwnedRepository.findById(id).orElse(null);

        if (questOwned == null) return "quest not exist";

        questOwned.updateCompleted(true);
        questOwnedRepository.save(questOwned);

        Quest quest = questOwned.getQuest();
        quest.updateRegistered(false);
        questRepository.save(quest);

        return "Quest completed";
    }
}

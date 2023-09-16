package com.prefin.component;

import com.prefin.domain.quest.QuestOwned;
import com.prefin.domain.user.Child;
import com.prefin.repository.quest.QuestOwnedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestScheduler {
    private final QuestOwnedRepository questOwnedRepository;

    // 매 정각 퀘스트 갱신
    @Transactional
    @Scheduled(cron = "0 * * * * ?", zone = "Asia/Seoul")  // "0 * * * * ?" 매 분, "* 0 0 * * ?" 매 자정
    public void RenewQuest() {
        // 정각 시간을 들고온다.
        Long endDate = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
//        Long endDate = 1694827885543L;
        List<QuestOwned> questOwnedList = questOwnedRepository.findByTime(endDate);

        System.out.println("퀘스트 완료");
        // 등록된 퀘스트를 완료로 처리
        // 퀘스트 등록을 false로 변경
        for (QuestOwned questOwned : questOwnedList) {
            questOwned.updateCompleted(true);
            questOwned.getQuest().updateRegistered(false);
        }
    }
}

package com.prefin.repository.quest;

import com.prefin.domain.quest.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<Quest, Long> {
}

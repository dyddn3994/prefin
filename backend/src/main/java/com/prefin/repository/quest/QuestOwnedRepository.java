package com.prefin.repository.quest;

import com.prefin.domain.quest.QuestOwned;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestOwnedRepository extends JpaRepository<QuestOwned, Long> {
}

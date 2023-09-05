package com.prefin.repository.quest;

import com.prefin.domain.quest.QuestOwned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestOwnedRepository extends JpaRepository<QuestOwned, Long> {
}

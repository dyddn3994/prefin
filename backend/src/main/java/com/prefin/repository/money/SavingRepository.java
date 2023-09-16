package com.prefin.repository.money;

import com.prefin.domain.money.SavingHistory;
import com.prefin.domain.quest.QuestOwned;
import com.prefin.domain.user.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingRepository extends JpaRepository<SavingHistory, Long> {
    @Query(value = "select savingHistory " +
            "from SavingHistory savingHistory " +
            "where savingHistory.child = :child")
    List<SavingHistory> findByChild(@Param("child") Child child);
}

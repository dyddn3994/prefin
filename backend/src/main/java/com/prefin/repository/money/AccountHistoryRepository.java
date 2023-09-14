package com.prefin.repository.money;

import com.prefin.domain.money.AccountHistory;
import com.prefin.domain.quest.QuestOwned;
import com.prefin.domain.user.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {
    @Query(value = "select accountHistory " +
            "from AccountHistory accountHistory " +
            "where accountHistory.child = :child")
    List<AccountHistory> findByChild(@Param("child") Child child);
}

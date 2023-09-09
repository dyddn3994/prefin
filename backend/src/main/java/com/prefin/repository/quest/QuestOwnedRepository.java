package com.prefin.repository.quest;

import com.prefin.domain.quest.Quest;
import com.prefin.domain.quest.QuestOwned;
import com.prefin.domain.user.Child;
import com.prefin.domain.user.Parents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestOwnedRepository extends JpaRepository<QuestOwned, Long> {
    @Query(value = "select questOwned " +
            "from QuestOwned questOwned " +
            "where questOwned.child = :child")
    List<QuestOwned> findByChild(@Param("child") Child child);
}

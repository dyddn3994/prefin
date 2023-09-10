package com.prefin.repository.quest;

import com.prefin.domain.quest.Quest;
import com.prefin.domain.user.Parents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    @Query(value = "select quest " +
            "from Quest quest " +
            "where quest.parent = :parent")
    List<Quest> findByParent(@Param("parent") Parents parent);
}

package com.prefin.repository.money;

import com.prefin.domain.money.SavingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingHistoryRepository extends JpaRepository<SavingHistory, Long> {
}

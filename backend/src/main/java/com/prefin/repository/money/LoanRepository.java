package com.prefin.repository.money;

import com.prefin.domain.money.LoanHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanHistory, Long> {
}

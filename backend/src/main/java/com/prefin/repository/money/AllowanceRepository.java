package com.prefin.repository.money;

import com.prefin.domain.money.Allowance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllowanceRepository extends JpaRepository<Allowance, Long> {
}

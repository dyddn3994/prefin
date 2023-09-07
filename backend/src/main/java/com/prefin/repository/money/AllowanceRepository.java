package com.prefin.repository.money;

import com.prefin.domain.money.Allowance;
import com.prefin.domain.user.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllowanceRepository extends JpaRepository<Allowance, Long> {
    Allowance findByParentId(Long parentId);
}

package com.prefin.repository.money;

import com.prefin.domain.money.Allowance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowanceRepository extends JpaRepository<Allowance, Long> {
    Allowance findByParentId(Long parentId);
}

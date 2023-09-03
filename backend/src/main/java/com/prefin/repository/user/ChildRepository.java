package com.prefin.repository.user;

import com.prefin.domain.user.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {
}

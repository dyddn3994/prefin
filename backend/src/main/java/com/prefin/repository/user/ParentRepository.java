package com.prefin.repository.user;

import com.prefin.domain.user.Parents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parents, Long> {
    Optional<Parents> findByUserId(String userId);
}

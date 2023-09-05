package com.prefin.repository.entertainment;

import com.prefin.domain.entertainment.Mascot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotRepository extends JpaRepository<Mascot, Long> {
}

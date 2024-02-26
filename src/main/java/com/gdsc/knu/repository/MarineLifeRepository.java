package com.gdsc.knu.repository;

import com.gdsc.knu.entity.MarineLife;
import com.gdsc.knu.entity.Waste;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MarineLifeRepository extends JpaRepository<MarineLife, Long> {
    List<MarineLife> findByUserId(Long userId);

    Optional<MarineLife> findByImageId(Long imageId);
}



package com.gdsc.knu.repository;

import com.gdsc.knu.entity.MarineLife;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MarineLifeRepository extends JpaRepository<MarineLife, Integer> {
    List<MarineLife> findByUserId(Integer userId);
}



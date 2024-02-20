package com.gdsc.knu.repository;

import com.gdsc.knu.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
    Optional<Achievement> findByUserId(Integer userID);
}

package com.gdsc.knu.repository;

import com.gdsc.knu.entity.Waste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WasteRepository extends JpaRepository<Waste, Integer> {
    Optional<Waste> findByUserId(Integer userID);
}

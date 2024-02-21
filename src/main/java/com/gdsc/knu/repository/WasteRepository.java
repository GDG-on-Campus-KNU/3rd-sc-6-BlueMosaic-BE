package com.gdsc.knu.repository;

import com.gdsc.knu.entity.Waste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WasteRepository extends JpaRepository<Waste, Long> {
    Optional<Waste> findByUserId(Long userID);
}

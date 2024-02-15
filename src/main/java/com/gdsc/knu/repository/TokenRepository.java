package com.gdsc.knu.repository;

import com.gdsc.knu.entity.RefreshToken;
import com.gdsc.knu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<RefreshToken, String> {
}

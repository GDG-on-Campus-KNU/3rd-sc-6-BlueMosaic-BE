package com.gdsc.knu.repository;

import com.gdsc.knu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByNicknameContaining(String nickname); // 닉네임을 포함하는 사용자 검색
}

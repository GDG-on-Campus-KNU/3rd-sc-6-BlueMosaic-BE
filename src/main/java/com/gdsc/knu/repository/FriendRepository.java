package com.gdsc.knu.repository;

import com.gdsc.knu.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUserId(Long userId);
    List<Friend> findByFriendUserId(Long friendUserId);

    Optional<Friend> findByUserIdAndFriendUserId(Long userId, Long friendUserId);
}

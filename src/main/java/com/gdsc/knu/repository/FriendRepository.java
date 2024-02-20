package com.gdsc.knu.repository;

import com.gdsc.knu.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Integer> {
    List<Friend> findByUserId(Integer userId);
    List<Friend> findByFriendUserId(Integer friendUserId);

    Optional<Friend> findByUserIdAndFriendUserId(Integer userId, Integer friendUserId);
}

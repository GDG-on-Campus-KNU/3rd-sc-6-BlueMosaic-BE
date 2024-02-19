package com.gdsc.knu.service;

import com.gdsc.knu.entity.Friend;
import com.gdsc.knu.repository.FriendRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendService {

    private final FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    public Friend save(Friend friend) {
        return friendRepository.save(friend);
    }

    public List<Friend> findByUserId(Integer userId) {
        return friendRepository.findByUserId(userId);
    }

    public void deleteByFriendId(Integer friendId) {
        friendRepository.deleteById(friendId);
    }
}


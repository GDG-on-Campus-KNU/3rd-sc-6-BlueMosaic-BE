package com.gdsc.knu.service;

import com.gdsc.knu.entity.Friend;
import com.gdsc.knu.entity.Ranking;
import com.gdsc.knu.entity.User;
import com.gdsc.knu.exception.NotFoundException;
import com.gdsc.knu.repository.FriendRepository;
import com.gdsc.knu.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class FriendService {

    private final FriendRepository friendRepository;

    private final UserRepository userRepository;

    public FriendService(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public Friend save(Friend friend) {
        return friendRepository.save(friend);
    }

    public List<Friend> findByUserId(Long userId) {
        return friendRepository.findByUserId(userId);
    }

    public void deleteByFriendId(Long friendId) {
        friendRepository.deleteById(friendId);
    }

    public void makefriendData(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NotFoundException("해당 사용자가 없습니다. id=" + authentication.getName()));

        Random random = new Random();
        long randomFriendId = 100L + random.nextInt(101);

        Friend friend = new Friend(user.getId(), 100L);
        friendRepository.save(friend);
    }

}


package com.gdsc.knu.controller;

import com.gdsc.knu.dto.FriendDTO;
import com.gdsc.knu.entity.Friend;
import com.gdsc.knu.entity.User;
import com.gdsc.knu.repository.FriendRepository;
import com.gdsc.knu.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FriendController {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @PostMapping("/friends")
    @Operation(summary = "친구 맺기", description = "친구 맺는 기능")
    public ResponseEntity<?> createFriend(@RequestBody FriendDTO friendDTO) {
        Optional<Friend> existingFriend = friendRepository.findByUserIdAndFriendUserId(friendDTO.getUserId(), friendDTO.getFriendUserId());

        if (existingFriend.isPresent()) {
            return new ResponseEntity<>("이미 친구입니다.", HttpStatus.BAD_REQUEST);
        }

        Friend friend = new Friend();
        friend.setUserId(friendDTO.getUserId());
        friend.setFriendUserId(friendDTO.getFriendUserId());
        Friend savedFriend = friendRepository.save(friend);
        return new ResponseEntity<>(savedFriend, HttpStatus.CREATED);
    }

    @GetMapping("/friends/{userID}")
    @Operation(summary = "친구 리스트 가져오기", description = "사용자의 친구 리스트 가져옴")
    public ResponseEntity<?> getFriendList(@PathVariable("userID") Long userID) {
        List<Friend> friendList1 = friendRepository.findByUserId(userID);
        List<Friend> friendList2 = friendRepository.findByFriendUserId(userID);

        if (friendList1.isEmpty() && friendList2.isEmpty()) {
            return new ResponseEntity<>("친구가 없습니다.", HttpStatus.OK);
        }

        List<User> friendUsers = new ArrayList<>();
        for (Friend friend : friendList1) {
            Optional<User> userOptional = userRepository.findById(Long.valueOf(friend.getFriendUserId()));
            if (!userOptional.isPresent()) {
                return new ResponseEntity<>("친구의 사용자 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            }
            friendUsers.add(userOptional.get());
        }

        for (Friend friend : friendList2) {
            Optional<User> userOptional = userRepository.findById(Long.valueOf(friend.getUserId()));
            if (!userOptional.isPresent()) {
                return new ResponseEntity<>("친구의 사용자 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            }
            friendUsers.add(userOptional.get());
        }

        return new ResponseEntity<>(friendUsers, HttpStatus.OK);
    }



    @DeleteMapping("/friends/{friendID}")
    @Operation(summary = "친구 삭제", description = "친구 삭제 기능")
    public ResponseEntity<Void> deleteFriend(@PathVariable("friendID") Long friendID) {
        friendRepository.deleteById(friendID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

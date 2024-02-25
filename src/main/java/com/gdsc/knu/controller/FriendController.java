package com.gdsc.knu.controller;

import com.gdsc.knu.dto.FriendDTO;
import com.gdsc.knu.entity.Friend;
import com.gdsc.knu.entity.User;
import com.gdsc.knu.repository.FriendRepository;
import com.gdsc.knu.repository.UserRepository;
import com.gdsc.knu.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private final FriendService friendService;

    @PostMapping("/friends")
    @Operation(summary = "친구 맺기", description = "친구 맺는 기능")
    public ResponseEntity<?> createFriend(@RequestBody FriendDTO friendDTO) {
        if (friendDTO.getUserId().equals(friendDTO.getFriendUserId())) {
            return new ResponseEntity<>("자신을 친구로 추가할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

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
            friendUsers.add(userOptional.get());
        }

        for (Friend friend : friendList2) {
            Optional<User> userOptional = userRepository.findById(Long.valueOf(friend.getUserId()));
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

    @GetMapping("/dummy-friend-data")
    @Operation(summary = "더미 친구 데이터 생성", description = "더미 친구 데이터 생성", responses = {
            @ApiResponse(responseCode = "200", description = "더미 친구 데이터 생성 성공", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> makeFriendData(Authentication authentication) {
        friendService.makefriendData(authentication);
        return ResponseEntity.ok("I'm making data!");
    }
}

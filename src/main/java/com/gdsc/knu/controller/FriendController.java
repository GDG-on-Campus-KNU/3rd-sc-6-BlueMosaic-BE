package com.gdsc.knu.controller;

import com.gdsc.knu.dto.FriendDTO;
import com.gdsc.knu.entity.Friend;
import com.gdsc.knu.entity.User;
import com.gdsc.knu.exception.ResourceNotFoundException;
import com.gdsc.knu.service.FriendService;
import com.gdsc.knu.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FriendController {

    private final FriendService friendService;
    private UserService userService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
        this.userService = userService;
    }

    @PostMapping("/friends")
    public ResponseEntity<Friend> createFriend(@RequestBody FriendDTO friendDTO) {
        Friend friend = new Friend();
        friend.setUserId(friendDTO.getUserId());
        friend.setFriendUserId(friendDTO.getFriendUserId());
        Friend savedFriend = friendService.save(friend);
        return new ResponseEntity<>(savedFriend, HttpStatus.CREATED);
    }


    @GetMapping("/friends/{userID}")
    public ResponseEntity<List<User>> getFriendList(@PathVariable("userID") Integer userID) {
        List<Friend> friendList = friendService.findByUserId(userID);
        List<User> friendUsers = new ArrayList<>();
        for (Friend friend : friendList) {
            User user = userService.findById(Long.valueOf(friend.getFriendUserId())).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
            friendUsers.add(user);
        }
        return new ResponseEntity<>(friendUsers, HttpStatus.OK);
    }


    @DeleteMapping("/friends/{friendID}")
    public ResponseEntity<Void> deleteFriend(@PathVariable("friendID") Integer friendID) {
        friendService.deleteByFriendId(friendID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}


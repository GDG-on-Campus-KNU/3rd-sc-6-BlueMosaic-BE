package com.gdsc.knu.controller;

import com.gdsc.knu.entity.Friend;
import com.gdsc.knu.service.FriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/friends")
    public ResponseEntity<Friend> createFriend(@RequestBody Friend friend) {
        Friend savedFriend = friendService.save(friend);
        return new ResponseEntity<>(savedFriend, HttpStatus.CREATED);
    }

    @GetMapping("/friends/{userID}")
    public ResponseEntity<List<Friend>> getFriendsByUserId(@PathVariable("userID") Integer userID) {
        List<Friend> friends = friendService.findByUserId(userID);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @DeleteMapping("/friends/{friendID}")
    public ResponseEntity<Void> deleteFriend(@PathVariable("friendID") Integer friendID) {
        friendService.deleteByFriendId(friendID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


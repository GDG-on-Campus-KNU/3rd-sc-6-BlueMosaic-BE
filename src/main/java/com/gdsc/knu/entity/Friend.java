package com.gdsc.knu.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer friendUserId;

    @CreatedDate
    private LocalDateTime addedAt;

    // 기본 생성자
    public Friend() {
    }

    // 모든 필드를 인자로 받는 생성자
    public Friend(Integer friendId, Integer userId, Integer friendUserId) {
        this.friendId = friendId;
        this.userId = userId;
        this.friendUserId = friendUserId;
    }

    // getter, setter 메서드
    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(Integer friendUserId) {
        this.friendUserId = friendUserId;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}

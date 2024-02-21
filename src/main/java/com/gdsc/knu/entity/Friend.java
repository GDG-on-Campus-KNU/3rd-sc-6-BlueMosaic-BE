package com.gdsc.knu.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long friendUserId;

    @CreatedDate
    private LocalDateTime addedAt;

    // 기본 생성자
    public Friend() {
    }

    // userId와 friendUserId를 인자로 받는 생성자
    public Friend(Long userId, Long friendUserId) {
        this.userId = userId;
        this.friendUserId = friendUserId;
    }

    // getter, setter 메서드
    public Long getFriendId() {
        return friendId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(Long friendUserId) {
        this.friendUserId = friendUserId;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

}

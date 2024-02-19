package com.gdsc.knu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import lombok.Builder;

@Entity
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@NoArgsConstructor
@Getter
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String name;
    @Column(unique = true)
    private String email;
    private boolean isLogin;
    private String region;
    private boolean deleted = Boolean.FALSE;

    public User(String nickname, String name, String email){
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.isLogin = true;
        this.region = "kr";
    }

    @Builder
    public User(Long id, String nickname, String name, String email, boolean isLogin, String region, boolean deleted){
        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.isLogin = isLogin;
        this.region = region;
        this.deleted = deleted;
    }

    public void update(String nickname, String name, String email, boolean isLogin, String region){
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.isLogin = isLogin;
        this.region = region;
    }

    public void updatePartial(String nickname, String name){
        this.nickname = nickname;
        this.name = name;
    }

    public void restore(){
        this.deleted = false;
    }
}

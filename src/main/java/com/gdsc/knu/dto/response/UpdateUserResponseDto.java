package com.gdsc.knu.dto.response;

import com.gdsc.knu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateUserResponseDto {
    private Long id;
    private String nickname;
    private String name;

    public UpdateUserResponseDto(User user){
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.name = user.getName();
    }
}

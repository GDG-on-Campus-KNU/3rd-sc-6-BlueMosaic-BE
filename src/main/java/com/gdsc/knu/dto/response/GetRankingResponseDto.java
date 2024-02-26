package com.gdsc.knu.dto.response;

import com.gdsc.knu.dto.UserRankingDto;
import com.gdsc.knu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetRankingResponseDto {
    private Long userId;
    private String userImageUrl;
    private String userName;
    private Long score;
    private String nickname;

    public GetRankingResponseDto(UserRankingDto userRankingDto){
        User user = userRankingDto.getUser();
        this.userId = user.getId();
        this.score = userRankingDto.getScore();
        this.userImageUrl = user.getProfileImageUrl();
        this.userName = user.getName();
        this.nickname = user.getNickname();
    }
}

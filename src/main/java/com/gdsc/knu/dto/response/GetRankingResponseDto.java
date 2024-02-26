package com.gdsc.knu.dto.response;

import com.gdsc.knu.dto.UserRankingDto;
import com.gdsc.knu.entity.Ranking;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetRankingResponseDto {
    private Long userId;
    private String userImageUrl;
    private String userName;
    private Long total;
    private String nickname;

    public GetRankingResponseDto(UserRankingDto userRankingDto){
        this.userId = userRankingDto.getUserId().getId();
        this.total = userRankingDto.getScore();
        this.userImageUrl = userRankingDto.getUserId().getProfileImageUrl();
        this.userName = userRankingDto.getUserId().getName();
    }
}

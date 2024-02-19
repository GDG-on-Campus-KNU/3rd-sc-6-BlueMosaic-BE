package com.gdsc.knu.dto.response;

import com.gdsc.knu.entity.Ranking;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetRankingResponseDto {
    private Long id;
    private Long userId;
    private int score;

    public GetRankingResponseDto(Ranking ranking){
        this.id = ranking.getId();
        this.userId = ranking.getUser().getId();
        this.score = ranking.getScore();
    }
}

package com.gdsc.knu.dto;

import com.gdsc.knu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRankingDto {
    private User userId;
    private Long score;
}

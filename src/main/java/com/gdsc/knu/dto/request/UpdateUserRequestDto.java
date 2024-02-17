package com.gdsc.knu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserRequestDto {
    private Long id;
    private String nickname;
    private String name;
}

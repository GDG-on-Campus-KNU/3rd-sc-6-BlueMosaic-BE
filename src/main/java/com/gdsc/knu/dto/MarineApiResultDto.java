package com.gdsc.knu.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MarineApiResultDto {
    private List<String> name;

    public MarineApiResultDto(String text) {
    }
}

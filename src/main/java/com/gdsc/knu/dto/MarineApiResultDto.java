package com.gdsc.knu.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MarineApiResultDto {
    // 해양 생물의 종류와 개수를 저장하는 DTO
    private int numberOfMarineLife;
    private int numberOfSpecies;

    public MarineApiResultDto(String text) {
        this.numberOfMarineLife = 0;
        String[] marineLife = text.split(",");
        this.numberOfSpecies = marineLife.length;
        for (String s : marineLife) {
            this.numberOfMarineLife += Integer.parseInt(s.split(":")[1].trim());
        }
    }
}

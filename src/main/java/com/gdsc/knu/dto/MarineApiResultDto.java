package com.gdsc.knu.dto;

import com.gdsc.knu.dto.response.MarinelifeUploadResponseDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
public class MarineApiResultDto {
    // 해양 생물의 종류와 개수를 저장하는 DTO
    private int numberOfMarineLife;
    private int numberOfSpecies;
    private int score;
    private List<MarinelifeUploadResponseDto.MarinelifeEntity> marineLife;

    public MarineApiResultDto(String text) {
        numberOfMarineLife = 0;
        numberOfSpecies = 0;
        marineLife = new ArrayList<>();

        String[] marineLifeArray = text.split("\n");
        for (String marineLife : marineLifeArray) {
            String[] marineLifeInfo = marineLife.split(":");
            if (marineLifeInfo.length != 2) continue;
            int num = Integer.parseInt(marineLifeInfo[1].replace(",", "").trim());
            String name = marineLifeInfo[0].replace("\"", "").trim();
            this.marineLife.add(new MarinelifeUploadResponseDto.MarinelifeEntity(name, num));
            numberOfMarineLife += num;
            numberOfSpecies++;
        }

        score = numberOfMarineLife * 2 + numberOfSpecies;
    }
}

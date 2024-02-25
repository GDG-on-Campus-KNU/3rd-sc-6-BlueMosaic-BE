package com.gdsc.knu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MarinelifeUploadResponseDto {
    private List<MarinelifeEntity> marinelife;
    private int score;
    private int total;

    @Getter
    public static class MarinelifeEntity {
        private String name;
        private int num;

        public MarinelifeEntity(String name, int num) {
            this.name = name;
            this.num = num;
        }
    }
}

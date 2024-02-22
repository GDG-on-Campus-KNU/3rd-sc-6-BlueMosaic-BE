package com.gdsc.knu.dto;

import lombok.Getter;

@Getter
public class WasteApiResultDto {
    private int plastic;
    private int styrofoam;
    private int fiber;
    private int vinyl;
    private int generalWaste;

    public WasteApiResultDto(String text) {
        plastic = 0;
        styrofoam = 0;
        fiber = 0;
        vinyl = 0;
        generalWaste = 0;
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.contains("plastic")) {
                plastic = parseValue(line);
            } else if (line.contains("styrofoam")) {
                styrofoam = parseValue(line);
            } else if (line.contains("fiber")) {
                fiber = parseValue(line);
            } else if (line.contains("vinyl")) {
                vinyl = parseValue(line);
            } else if (line.contains("generalWaste")) {
                generalWaste = parseValue(line);
            }
        }
    }

    private int parseValue(String line) {
        return Integer.parseInt(line.split(":")[1].trim().replace(",", ""));
    }
}

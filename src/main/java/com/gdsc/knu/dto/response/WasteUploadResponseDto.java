package com.gdsc.knu.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WasteUploadResponseDto {
    private int plastic;
    private int styrofoam;
    private int fiber;
    private int vinyl;
    private int generalWaste;
    private int score;
    private int total;
}

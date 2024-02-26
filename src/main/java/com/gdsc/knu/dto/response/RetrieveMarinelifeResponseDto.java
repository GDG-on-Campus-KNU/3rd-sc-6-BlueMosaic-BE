package com.gdsc.knu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RetrieveMarinelifeResponseDto {
    // image, className, date
    private String base64EncodedImage;
    private String className;
    private String date;
    private Long imageId;
}

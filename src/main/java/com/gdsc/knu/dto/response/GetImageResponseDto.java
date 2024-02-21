package com.gdsc.knu.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetImageResponseDto {
    private String fileName;
    private String fileType;
    private String base64EncodedImage;
}

package com.gdsc.knu.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetImageResponseDto {
    private Long mediaFileId;
    private Long userId;
    private String url;
    private String fileName;
    private String fileType;
    private String base64EncodedImage;
}

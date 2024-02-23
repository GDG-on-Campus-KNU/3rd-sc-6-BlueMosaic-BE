package com.gdsc.knu.controller;

import com.gdsc.knu.dto.response.GetImageResponseDto;
import com.gdsc.knu.exception.ResourceNotFoundException;
import com.gdsc.knu.service.MediaFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/media")
public class MediaFileController {
    private final MediaFileService mediaFileService;

    @GetMapping("/{id}")
    @Operation(summary = "파일 조회", description = "파일을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "파일 조회 성공", content = @Content(schema = @Schema(implementation = GetImageResponseDto.class)))
    })
    public ResponseEntity<GetImageResponseDto> getFile(@PathVariable("id") Long id) {
        GetImageResponseDto file = mediaFileService.getFile(id);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "파일 삭제", description = "파일을 삭제합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "파일 삭제 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "파일 삭제 실패", content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    public ResponseEntity<?> deleteFile(@PathVariable("id") Long id) {
        mediaFileService.deleteFile(id);
        return ResponseEntity.ok("File deleted successfully");
    }
}

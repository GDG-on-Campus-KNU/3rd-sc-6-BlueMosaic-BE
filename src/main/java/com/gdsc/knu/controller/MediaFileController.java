package com.gdsc.knu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gdsc.knu.dto.response.GetImageResponseDto;
import com.gdsc.knu.entity.MediaFile;
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
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/media")
public class MediaFileController {
    private final MediaFileService mediaFileService;

    @PostMapping("/upload")
    @Operation(summary = "파일 업로드", description = "파일을 업로드합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "파일 업로드 성공", content = @Content(schema = @Schema(implementation = MediaFile.class)))
    })
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        MediaFile savedFile = mediaFileService.saveFile(file);
        // TODO : google api 호출 및 return 수정.
        mediaFileService.processImageAndCallExternalAPI(savedFile);
        return ResponseEntity.ok(savedFile);
    }

    @GetMapping("/{id}")
    @Operation(summary = "파일 조회", description = "파일을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "파일 조회 성공", content = @Content(schema = @Schema(implementation = MediaFile.class)))
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

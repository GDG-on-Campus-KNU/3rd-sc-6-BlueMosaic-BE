package com.gdsc.knu.controller;

import com.gdsc.knu.entity.MediaFile;
import com.gdsc.knu.service.MediaFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        MediaFile savedFile = mediaFileService.saveFile(file);
        return ResponseEntity.ok(savedFile);
    }

    @GetMapping("/{id}")
    @Operation(summary = "파일 조회", description = "파일을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "파일 조회 성공", content = @Content(schema = @Schema(implementation = MediaFile.class)))
    })
    public ResponseEntity<?> getFile(@PathVariable Long id) {
        MediaFile file = mediaFileService.getFile(id);
        return ResponseEntity.ok(file);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "파일 삭제", description = "파일을 삭제합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "파일 삭제 성공", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        mediaFileService.deleteFile(id);
        return ResponseEntity.ok("File deleted successfully");
    }

    @PutMapping("/{id}")
    @Operation(summary = "파일 수정", description = "파일을 수정합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "파일 수정 성공", content = @Content(schema = @Schema(implementation = MediaFile.class)))
    })
    public ResponseEntity<?> updateFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        MediaFile updatedFile = mediaFileService.updateFile(id, file);
        return ResponseEntity.ok(updatedFile);
    }
}

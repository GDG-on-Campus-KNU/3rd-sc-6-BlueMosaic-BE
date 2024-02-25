package com.gdsc.knu.controller;

import com.gdsc.knu.dto.response.GetRankingResponseDto;
import com.gdsc.knu.exception.NotFoundException;
import com.gdsc.knu.service.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {
    private final RankingService rankingService;

    @GetMapping()
    @Operation(summary = "랭킹 조회", description = "랭킹을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "랭킹 조회 성공", content = @Content(schema = @Schema(implementation = GetRankingResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "랭킹 조회 실패", content = @Content(schema = @Schema(implementation = NotFoundException.class)))
    })
    public ResponseEntity<List<GetRankingResponseDto>> getRankingTOP100() {
        List<GetRankingResponseDto> rankingList = rankingService.getRankingTOP100();
        return new ResponseEntity<>(rankingList, HttpStatus.OK);
    }

    @GetMapping("/test-making-data")
    @Operation(summary = "테스트 데이터 생성", description = "테스트 데이터 생성", responses = {
            @ApiResponse(responseCode = "200", description = "테스트 데이터 생성 성공", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> testMakingData(Authentication authentication) {
        rankingService.makeTestData(authentication);
        return ResponseEntity.ok("I'm making data!");
    }

    @GetMapping("/friend-ranking-data")
    @Operation(summary = "친구 테스트 데이터 생성", description = "친구 테스트 데이터 생성", responses = {
            @ApiResponse(responseCode = "200", description = "테스트 데이터 생성 성공", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> friendRankingData() {
        rankingService.makeFriendRankingData();
        return ResponseEntity.ok("I'm making data!");
    }
}
package com.gdsc.knu.controller;

import com.gdsc.knu.service.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {
    private final RankingService rankingService;

    @GetMapping()
    public ResponseEntity<?> getRanking() {
        return ResponseEntity.ok("I'm ranking!");
    }

    @GetMapping("/test-making-data")
    public ResponseEntity<?> testMakingData() {
//        rankingService.makeTestData();
        return ResponseEntity.ok("I'm making data!");
    }
}
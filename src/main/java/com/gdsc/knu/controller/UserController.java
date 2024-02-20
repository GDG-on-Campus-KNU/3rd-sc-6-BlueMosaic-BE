package com.gdsc.knu.controller;

import com.gdsc.knu.dto.request.UpdateUserRequestDto;
import com.gdsc.knu.dto.response.GetUserResponseDto;
import com.gdsc.knu.dto.response.UpdateUserResponseDto;
import com.gdsc.knu.exception.ForbiddenException;
import com.gdsc.knu.exception.NotFoundException;
import com.gdsc.knu.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;


    @GetMapping("/{id}")
    @Operation(summary = "유저 정보 조회", description = "유저 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "유저 정보 조회 실패", content = @Content(schema = @Schema(implementation = NotFoundException.class)))
            })
    public ResponseEntity<GetUserResponseDto> getUser(@PathVariable("id") Long id) {
        GetUserResponseDto user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping()
    @Operation(summary = "유저 정보 수정", description = "유저 정보를 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 정보 수정 성공"),
                    @ApiResponse(responseCode = "404", description = "유저 정보 조회 실패", content = @Content(schema = @Schema(implementation = NotFoundException.class))),
                    @ApiResponse(responseCode = "403", description = "유저 정보 수정 권한 없음", content = @Content(schema = @Schema(implementation = ForbiddenException.class)))
            })
    public ResponseEntity<UpdateUserResponseDto> updateUser(@Valid @RequestBody UpdateUserRequestDto updateUserRequestDto, Authentication authentication) {
        UpdateUserResponseDto updateUserResponseDto = userService.updateUser(updateUserRequestDto, authentication);
        return new ResponseEntity<>(updateUserResponseDto, HttpStatus.OK);
    }

    @GetMapping("/search/{nickname}")
    @Operation(summary = "닉네임으로 유저 검색", description = "닉네임으로 유저를 검색합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 검색 성공"),
                    @ApiResponse(responseCode = "404", description = "유저 검색 실패", content = @Content(schema = @Schema(implementation = NotFoundException.class)))
            })
    public ResponseEntity<List<GetUserResponseDto>> searchUserWithNickname(@PathVariable("nickname") String nickname) {
        List<GetUserResponseDto> user = userService.searchUserWithNickname(nickname);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<GetUserResponseDto> getMe(Authentication authentication) {
        GetUserResponseDto user = userService.getMe(authentication);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

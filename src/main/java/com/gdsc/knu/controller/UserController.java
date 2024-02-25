package com.gdsc.knu.controller;

import com.gdsc.knu.dto.UserDto;
import com.gdsc.knu.dto.request.UpdateUserRequestDto;
import com.gdsc.knu.dto.response.GetUserResponseDto;
import com.gdsc.knu.dto.response.UpdateUserResponseDto;
import com.gdsc.knu.exception.ForbiddenException;
import com.gdsc.knu.exception.NotFoundException;
import com.gdsc.knu.service.RankingService;
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

    private final RankingService rankingService;


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

    @PostMapping("/create/dummy")
    public ResponseEntity<?> createDummyUser() {
        UserDto userDto = new UserDto();
        userDto.setId(2L);
        userDto.setNickname("Happy");
        userDto.setName("Happy");
        userDto.setEmail("Happy@example.com");
        userDto.setLogin(false);
        userDto.setRegion("kr");
        userDto.setDeleted(false);
        userDto.setProfileImageUrl("https://mblogthumb-phinf.pstatic.net/MjAyMDExMDFfMyAg/MDAxNjA0MjI5NDA4NDMy.5zGHwAo_UtaQFX8Hd7zrDi1WiV5KrDsPHcRzu3e6b8Eg.IlkR3QN__c3o7Qe9z5_xYyCyr2vcx7L_W1arNFgwAJwg.JPEG.gambasg/%EC%9C%A0%ED%8A%9C%EB%B8%8C_%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84_%ED%8C%8C%EC%8A%A4%ED%85%94.jpg?type=w800");

        userService.createDummyUser(userDto);

        UserDto userDto2 = new UserDto();
        userDto2.setId(3L);
        userDto2.setNickname("Friend");
        userDto2.setName("Best Friend");
        userDto2.setEmail("friend@example.com");
        userDto2.setLogin(false);
        userDto2.setRegion("kr");
        userDto2.setDeleted(false);
        userDto2.setProfileImageUrl("https://i.namu.wiki/i/Q0N8jhw_skyBQq8Xyt71IjGjuHOwDz1MsHEV3n1DxdNDXEHcfVj1eTyQXRMDt1gI_CXhCi78Xpi8PB45ept8BQ.webp");

        userService.createDummyUser(userDto2);
        return ResponseEntity.ok("Dummy user created successfully");
    }
}

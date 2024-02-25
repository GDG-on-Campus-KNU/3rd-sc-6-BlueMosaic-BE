package com.gdsc.knu.controller;

import com.gdsc.knu.dto.response.GetImageResponseDto;
import com.gdsc.knu.dto.response.MarinelifeUploadResponseDto;
import com.gdsc.knu.dto.response.RetrieveMarinelifeResponseDto;
import com.gdsc.knu.entity.MarineLife;
import com.gdsc.knu.entity.MediaFile;
import com.gdsc.knu.repository.MarineLifeRepository;
import com.gdsc.knu.service.MarineLifeService;
import com.gdsc.knu.service.MediaFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marinelife")
@RequiredArgsConstructor
public class MarineLifeController {
    private final MarineLifeRepository marineLifeRepository;
    private final MediaFileService mediaFileService;
    private final MarineLifeService marineLifeService;

    @PostMapping
    @Operation(summary = "해양 생물 정보 업로드", description = "사용자의 해양 생물 정보를 업로드한다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "해양 생물 정보 업로드 성공", content = @Content(schema = @Schema(implementation = MarinelifeUploadResponseDto.class)))
            })
    public ResponseEntity<MarinelifeUploadResponseDto> uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) {
        GetImageResponseDto getImageResponseDto = mediaFileService.saveFile(authentication, file, "marinelife");
        MarinelifeUploadResponseDto marinelifeUploadResponseDto = marineLifeService.processMarineImageAnalysis(getImageResponseDto);
        return new ResponseEntity<>(marinelifeUploadResponseDto, HttpStatus.OK);
    }

    @PostMapping("/temp-data")
    @Operation(summary = "해양 생물 정보 임의 생성", description = "사용자의 해양 생물 정보를 생성한다")
    public ResponseEntity<MarineLife> createMarineLife(@RequestBody MarineLife marineLife) {
        MarineLife savedMarineLife = marineLifeRepository.save(marineLife);
        return new ResponseEntity<>(savedMarineLife, HttpStatus.CREATED);
    }

    // 사용자가 모은 해양 생물 정보 조회
    @GetMapping("/retrieve/{userID}")
    @Operation(summary = "사용자가 모은 해양 생물 정보 조회", description = "사용자가 모은 해양 생물 정보를 조회한다")
    public ResponseEntity<List<RetrieveMarinelifeResponseDto>> getMarineLifeByUser(@PathVariable("userID") Long userID) {
        List<MarineLife> marineLifeList = marineLifeRepository.findByUserId(userID);
        List<RetrieveMarinelifeResponseDto> retrieveMarinelifeResponseDtoList = new ArrayList<>();
        for (MarineLife marineLife : marineLifeList) {
            retrieveMarinelifeResponseDtoList.add(new RetrieveMarinelifeResponseDto(mediaFileService.getFile(marineLife.getImageId()).getBase64EncodedImage(), marineLife.getClassName(), marineLife.getCreatedDate().toString(), marineLife.getImageId()));
        }
        return new ResponseEntity<>(retrieveMarinelifeResponseDtoList, HttpStatus.OK);
    }

    // 해양 생물 정보 수정
    @PutMapping("/marinelife/{marineLifeID}")
    @Operation(summary = "해양 생물 정보 수정", description = "주어진 ID의 해양 생물 정보를 수정한다")
    public ResponseEntity<?> updateMarineLife(@PathVariable Long marineLifeID, @RequestBody MarineLife newMarineLife) {
        Optional<MarineLife> marineLife = marineLifeRepository.findById(marineLifeID);
        if (!marineLife.isPresent()) {
            return new ResponseEntity<>("해당 ID의 해양 생물 정보가 없습니다.", HttpStatus.NOT_FOUND);
        }
        MarineLife oldMarineLife = marineLife.get();
        oldMarineLife.setUserId(newMarineLife.getUserId());
        oldMarineLife.setName(newMarineLife.getName());
        oldMarineLife.setLatitude(newMarineLife.getLatitude());
        oldMarineLife.setLongitude(newMarineLife.getLongitude());
        marineLifeRepository.save(oldMarineLife);
        return new ResponseEntity<>("해양 생물 정보가 성공적으로 수정되었습니다.", HttpStatus.OK);
    }

    // 해양 생물 정보 삭제
    @DeleteMapping("/delete/{marineLifeID}")
    @Operation(summary = "해양 생물 정보 삭제", description = "주어진 ID의 해양 생물 정보를 삭제한다")
    public ResponseEntity<?> deleteMarineLife(@PathVariable Long marineLifeID) {
        Optional<MarineLife> marineLife = marineLifeRepository.findById(marineLifeID);
        if (!marineLife.isPresent()) {
            return new ResponseEntity<>("해당 ID의 해양 생물 정보가 없습니다.", HttpStatus.NOT_FOUND);
        }
        marineLifeRepository.delete(marineLife.get());
        return new ResponseEntity<>("해양 생물 정보가 성공적으로 삭제되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/friend-dummy-marine")
    @Operation(summary = "친구 해양 파일 업로드", description = "친구 해양 이미지를 업로드하고 점수를 측정합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "파일 업로드 성공", content = @Content(schema = @Schema(implementation = GetImageResponseDto.class)))
    })
    public ResponseEntity<GetImageResponseDto> uploaddummyFile(@RequestParam("file") MultipartFile file) {
        GetImageResponseDto getImageResponseDto = mediaFileService.savedummyFile(file, "marinelife");
        marineLifeService.processMarineImageAnalysis(getImageResponseDto);
        return new ResponseEntity<>(getImageResponseDto, HttpStatus.OK);
    }
}
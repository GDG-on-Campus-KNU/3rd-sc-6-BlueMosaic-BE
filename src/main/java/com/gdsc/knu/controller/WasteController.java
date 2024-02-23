package com.gdsc.knu.controller;

import com.gdsc.knu.dto.response.GetImageResponseDto;
import com.gdsc.knu.entity.Waste;
import com.gdsc.knu.repository.WasteRepository;
import com.gdsc.knu.service.MediaFileService;
import com.gdsc.knu.service.WasteService;
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

import java.util.Optional;

@RestController
@RequestMapping("/waste")
@RequiredArgsConstructor
public class WasteController {
    private final WasteRepository wasteRepository;
    private final MediaFileService mediaFileService;
    private final WasteService wasteService;

    @PostMapping
    @Operation(summary = "쓰레기 파일 업로드", description = "쓰레기 이미지를 업로드하고 점수를 측정합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "파일 업로드 성공", content = @Content(schema = @Schema(implementation = GetImageResponseDto.class)))
    })
    public ResponseEntity<GetImageResponseDto> uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) {
        GetImageResponseDto getImageResponseDto = mediaFileService.saveFile(authentication, file);
        wasteService.processWasteImageAnalysis(getImageResponseDto);
        return new ResponseEntity<>(getImageResponseDto, HttpStatus.OK);
    }

    @PostMapping("/temp-data")
    @Operation(summary = "임시 쓰레기 데이터 생성 ", description = "업적 확인을 위한 쓰레기 데이터 생성")
    public ResponseEntity<Waste> createWaste(@RequestBody Waste newWaste) {
        Optional<Waste> existingWaste = wasteRepository.findById(newWaste.getUserId());

        if (((Optional<?>) existingWaste).isPresent()) {
            Waste wasteToUpdate = existingWaste.get();
            wasteToUpdate.setPlastic(wasteToUpdate.getPlastic() + newWaste.getPlastic());
            wasteToUpdate.setStyrofoam(wasteToUpdate.getStyrofoam() + newWaste.getStyrofoam());
            wasteToUpdate.setFiber(wasteToUpdate.getFiber() + newWaste.getFiber());
            wasteToUpdate.setVinyl(wasteToUpdate.getVinyl() + newWaste.getVinyl());
            wasteToUpdate.setGeneralWaste(wasteToUpdate.getGeneralWaste() + newWaste.getGeneralWaste());

            Waste updatedWaste = wasteRepository.save(wasteToUpdate);
            return ResponseEntity.ok(updatedWaste);
        } else {
            newWaste.setWasteId(null);
            Waste createdWaste = wasteRepository.save(newWaste);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWaste);
        }
    }

}

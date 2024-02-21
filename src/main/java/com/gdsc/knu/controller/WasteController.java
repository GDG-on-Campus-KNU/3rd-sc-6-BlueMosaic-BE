package com.gdsc.knu.controller;

import com.gdsc.knu.dto.wasteResultDto;
import com.gdsc.knu.entity.Waste;
import com.gdsc.knu.repository.WasteRepository;
import com.gdsc.knu.service.WasteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Wastes")
public class WasteController {
    private final WasteRepository wasteRepository;
    private final WasteService wasteService;

    public WasteController(WasteRepository wasteRepository, WasteService wasteService) {
        this.wasteRepository = wasteRepository;
        this.wasteService = wasteService;
    }

    @GetMapping("identify/{userId}/{fileId}")
    @Operation(summary = "쓰레기 이미지 종류/점수/총점수", description = "점수 확인")
    public ResponseEntity<wasteResultDto> getWasteInfo(@PathVariable Long userId, @PathVariable Long fileId) {
        wasteResultDto result = wasteService.getResults(userId, fileId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    @Operation(summary = "임시 쓰레기 데이터 생성 ", description = "업적 확인을 위한 쓰레기 데이터 생성")
    public ResponseEntity<Waste> createWaste(@RequestBody Waste newWaste) {
        Optional<Waste> existingWaste = wasteRepository.findById(newWaste.getUserId());

        if (existingWaste.isPresent()) {
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

package com.gdsc.knu.controller;

import com.gdsc.knu.entity.MarineLife;
import com.gdsc.knu.repository.MarineLifeRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marinelife")
public class MarineLifeController {

    @Autowired
    private MarineLifeRepository marineLifeRepository;

    // 해양 생물 정보 생성

    @PostMapping("/create")
    @Operation(summary = "해양 생물 정보 생성", description = "사용자의 해양 생물 정보를 생성한다")
    public ResponseEntity<MarineLife> createMarineLife(@RequestBody MarineLife marineLife) {
        MarineLife savedMarineLife = marineLifeRepository.save(marineLife);
        return new ResponseEntity<>(savedMarineLife, HttpStatus.CREATED);
    }

    // 사용자가 모은 해양 생물 정보 조회
    @GetMapping("/retrieve/{userID}")
    @Operation(summary = "사용자가 모은 해양 생물 정보 조회", description = "사용자가 모은 해양 생물 정보를 조회한다")
    public ResponseEntity<?> getMarineLifeByUser(@PathVariable Integer userID) {
        List<MarineLife> marineLifeList = marineLifeRepository.findByUserId(userID);
        if (marineLifeList.isEmpty()) {
            return new ResponseEntity<>("해당 사용자가 모은 해양 생물 정보가 없습니다.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(marineLifeList, HttpStatus.OK);
    }

    // 해양 생물 정보 수정
    @PutMapping("/modify/{userID}/{marineLifeID}")
    @Operation(summary = "사용자가 해양 생물 정보 수정하는 기능", description = "사용자가 모은 해양 생물 정보를 수정한다")
    public ResponseEntity<?> updateMarineLife(@PathVariable Integer userID, @PathVariable Integer marineLifeID, @RequestBody MarineLife newMarineLife) {
        Optional<MarineLife> optionalMarineLife = marineLifeRepository.findById(marineLifeID);
        if (!optionalMarineLife.isPresent()) {
            return new ResponseEntity<>("해양 생물 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        MarineLife marineLife = optionalMarineLife.get();
        if (!marineLife.getUserId().equals(userID)) {
            return new ResponseEntity<>("해당 사용자가 모은 해양 생물 정보가 아닙니다.", HttpStatus.FORBIDDEN);
        }
        marineLife.setName(newMarineLife.getName());
        marineLife.setLatitude(newMarineLife.getLatitude());
        marineLife.setLongitude(newMarineLife.getLongitude());
        marineLife.setFirstFounderId(newMarineLife.getFirstFounderId());
        marineLifeRepository.save(marineLife);
        return new ResponseEntity<>(marineLife, HttpStatus.OK);
    }

    // 해양 생물 정보 삭제
    @DeleteMapping("/delete/{userID}")
    @Operation(summary = "사용자가 모은 해양 생물 정보 삭제", description = "사용자가 생각하기에 이상한 것이 해양 생물로 들어갔다면, 해양 생물 정보를 삭제한다")
    public ResponseEntity<?> deleteMarineLife(@PathVariable Integer userID, @PathVariable Integer marineLifeID) {
        Optional<MarineLife> optionalMarineLife = marineLifeRepository.findById(marineLifeID);
        if (!optionalMarineLife.isPresent()) {
            return new ResponseEntity<>("해양 생물 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        MarineLife marineLife = optionalMarineLife.get();
        if (!marineLife.getUserId().equals(userID)) {
            return new ResponseEntity<>("해당 사용자가 모은 해양 생물 정보가 아닙니다.", HttpStatus.FORBIDDEN);
        }
        marineLifeRepository.delete(marineLife);
        return new ResponseEntity<>("해양 생물 정보가 삭제되었습니다.", HttpStatus.OK);
    }
}

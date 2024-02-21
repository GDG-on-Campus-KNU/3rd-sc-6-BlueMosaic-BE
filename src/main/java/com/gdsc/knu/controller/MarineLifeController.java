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
    @Operation(summary = "해양 생물 정보 임의 생성", description = "사용자의 해양 생물 정보를 생성한다")
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
    @PutMapping("/marinelife/{marineLifeID}")
    @Operation(summary = "해양 생물 정보 수정", description = "주어진 ID의 해양 생물 정보를 수정한다")
    public ResponseEntity<?> updateMarineLife(@PathVariable Integer marineLifeID, @RequestBody MarineLife newMarineLife) {
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
    public ResponseEntity<?> deleteMarineLife(@PathVariable Integer marineLifeID) {
        Optional<MarineLife> marineLife = marineLifeRepository.findById(marineLifeID);
        if (!marineLife.isPresent()) {
            return new ResponseEntity<>("해당 ID의 해양 생물 정보가 없습니다.", HttpStatus.NOT_FOUND);
        }
        marineLifeRepository.delete(marineLife.get());
        return new ResponseEntity<>("해양 생물 정보가 성공적으로 삭제되었습니다.", HttpStatus.OK);
    }

}

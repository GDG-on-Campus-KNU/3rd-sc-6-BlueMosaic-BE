package com.gdsc.knu.controller;

import com.gdsc.knu.entity.MarineLife;
import com.gdsc.knu.repository.MarineLifeRepository;
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
    public ResponseEntity<MarineLife> createMarineLife(@RequestBody MarineLife marineLife) {
        MarineLife savedMarineLife = marineLifeRepository.save(marineLife);
        return new ResponseEntity<>(savedMarineLife, HttpStatus.CREATED);
    }

    // 사용자가 모은 해양 생물 정보 조회
    @GetMapping("/retrieve/{userID}")
    public ResponseEntity<?> getMarineLifeByUser(@PathVariable Integer userID) {
        List<MarineLife> marineLifeList = marineLifeRepository.findByUserId(userID);
        if (marineLifeList.isEmpty()) {
            return new ResponseEntity<>("해당 사용자가 모은 해양 생물 정보가 없습니다.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(marineLifeList, HttpStatus.OK);
    }

    // 해양 생물 정보 수정
    @PutMapping("/modify/{userID}/{marineLifeID}")
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
    @DeleteMapping("/delete/{userID}/{marineLifeID}")
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

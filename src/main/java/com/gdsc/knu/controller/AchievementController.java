package com.gdsc.knu.controller;

import com.gdsc.knu.dto.request.AchievementRequest;
import com.gdsc.knu.entity.Achievement;
import com.gdsc.knu.entity.Waste;
import com.gdsc.knu.repository.AchievementRepository;
import com.gdsc.knu.repository.WasteRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/achievements")
public class AchievementController {

    private final AchievementRepository achievementRepository;
    private final WasteRepository wasteRepository;
    public AchievementController(AchievementRepository achievementRepository, WasteRepository wasteRepository) {
        this.achievementRepository = achievementRepository;
        this.wasteRepository = wasteRepository;
    }

    @PostMapping("update")
    @Operation(summary = "업적 업데이트", description = "업적 업데이트 기능")
    public ResponseEntity<Achievement> createOrUpdateAchievement(@RequestBody AchievementRequest request) {
        Integer userID = request.getUserId();

        Optional<Achievement> existingAchievementOptional = achievementRepository.findByUserId(userID);
        Achievement achievement = existingAchievementOptional.orElse(new Achievement());

        Optional<Waste> wasteOptional = wasteRepository.findByUserId(userID);

        if (wasteOptional.isPresent()) {
            Waste waste = wasteOptional.get();
            int wasteCount = 0;

            if (waste.getPlastic() > 0) wasteCount++;
            if (waste.getStyrofoam() > 0) wasteCount++;
            if (waste.getFiber() > 0) wasteCount++;
            if (waste.getVinyl() > 0) wasteCount++;
            if (waste.getGeneralWaste() > 0) wasteCount++;

            // 쓰레기 종류 업적 설정
            if (wasteCount >= 1) achievement.setSproutCleaner011(true);
            if (wasteCount >= 3) achievement.setExperiencedCleaner012(true);
            if (wasteCount == 5) achievement.setSkilledCleaner013(true);

            float garbageCollectionDegree = ((float) wasteCount / 5) * 100;
            achievement.setGarbageCollectionDegree(garbageCollectionDegree);
        }

        // 해양 별 도감 업적 설정
        float marineLifeCollectionRate = achievement.getMarineLifeCollectionRate();
        if (marineLifeCollectionRate >= 1) achievement.setNoviceDiver021(true);
        if (marineLifeCollectionRate >= 30) achievement.setPromisingDiver022(true);
        if (marineLifeCollectionRate >= 50) achievement.setExperiencedDiver023(true);
        if (marineLifeCollectionRate >= 80) achievement.setSkilledDiver024(true);
        if (marineLifeCollectionRate == 100) achievement.setDolphin025(true);

    /*
    // 물고기 종류 업적 설정
    // 물고기 종류 수를 가져오는 로직이 필요
    int fishCount = getFishCount(userID);
    if (fishCount >= 1) achievement.setNovicePhotographer(true);
    if (fishCount >= 3) achievement.setExperiencedPhotographer(true);
    if (fishCount >= 5) achievement.setPopularPhotographer(true);
    if (fishCount == 10) achievement.setPaparazzi(true);
     */

        achievement.setUserId(userID);
        Achievement createdOrUpdateAchievement = achievementRepository.save(achievement);

        return ResponseEntity.ok(createdOrUpdateAchievement);
    }

    @PostMapping("check")
    @Operation(summary = "업적 확인", description = "업적 확인")
    public ResponseEntity<Achievement> getAchievementByUserId(@RequestBody AchievementRequest request) {
        Integer userID = request.getUserId();

        Optional<Achievement> achievementOptional = achievementRepository.findByUserId(userID);
        if (!achievementOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(achievementOptional.get());
    }

}

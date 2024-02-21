package com.gdsc.knu.service;

import com.gdsc.knu.dto.GoogleApiResultDto;
import com.gdsc.knu.entity.Waste;
import com.gdsc.knu.repository.WasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WasteService {
    @Autowired
    private WasteRepository wasteRepository;

    public int calculateWasteScore(long userId, GoogleApiResultDto googleApiResultDto) {
        try {
            Waste waste = wasteRepository.findById(userId).orElseGet(Waste::new);
            if (waste.getUserId() == null) {
                waste.setUserId(userId);
            }

            // 각 쓰레기 개수 업데이트
            waste.setPlastic(waste.getPlastic() + googleApiResultDto.getPlastic());
            waste.setStyrofoam(waste.getStyrofoam() + googleApiResultDto.getStyrofoam());
            waste.setFiber(waste.getFiber() + googleApiResultDto.getFiber());
            waste.setVinyl(waste.getVinyl() + googleApiResultDto.getVinyl());
            waste.setGeneralWaste(waste.getGeneralWaste() + googleApiResultDto.getGeneralWaste());

            // 총 점수 계산 (가정: 각 쓰레기는 1점)
            int totalScore = waste.getPlastic() + waste.getStyrofoam() + waste.getFiber() + waste.getVinyl() + waste.getGeneralWaste();

            // 업데이트된 정보를 데이터베이스에 저장
            wasteRepository.save(waste);

            // 총 점수 반환
            return totalScore;
        } catch (Exception ex) {
            throw new RuntimeException("쓰레기 점수 계산 중 오류가 발생, 다시 시도해주세요.", ex);
        }
    }
}

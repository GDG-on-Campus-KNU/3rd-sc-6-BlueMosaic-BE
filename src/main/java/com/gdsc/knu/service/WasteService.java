package com.gdsc.knu.service;

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

    public int calculateWasteScore(int userId, int plastic, int styrofoam, int fiber, int vinyl, int general) {
        try {
            Optional<Waste> wasteOptional = wasteRepository.findById(userId);

            if (!wasteOptional.isPresent()) {
                throw new NoSuchElementException("해당 사용자의 쓰레기 정보를 찾을 수 없습니다.");
            }

            Waste waste = wasteOptional.get();

            // 각 쓰레기 개수 업데이트
            waste.setPlastic(waste.getPlastic() + plastic);
            waste.setStyrofoam(waste.getStyrofoam() + styrofoam);
            waste.setFiber(waste.getFiber() + fiber);
            waste.setVinyl(waste.getVinyl() + vinyl);
            waste.setGeneralWaste(waste.getGeneralWaste() + general);

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

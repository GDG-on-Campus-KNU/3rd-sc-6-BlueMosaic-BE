package com.gdsc.knu.service;

import com.gdsc.knu.dto.GoogleApiResultDto;
import com.gdsc.knu.dto.wasteResultDto;
import com.gdsc.knu.entity.MediaFile;
import com.gdsc.knu.entity.Waste;
import com.gdsc.knu.exception.ResourceNotFoundException;
import com.gdsc.knu.repository.MediaFileRepository;
import com.gdsc.knu.repository.WasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WasteService {
    @Autowired
    private WasteRepository wasteRepository;
    private final MediaFileRepository mediaFileRepository;

    public WasteService(MediaFileRepository mediaFileRepository) {
        this.wasteRepository = wasteRepository;
        this.mediaFileRepository = mediaFileRepository;
    }

    public wasteResultDto getResults(Long userId, Long fileId) {
        MediaFile file = mediaFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id " + fileId));

        if (!file.getUserId().equals(userId)) {
            throw new IllegalArgumentException("User ID and file owner do not match.");
        }

        Waste waste = wasteRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        int totalWaste = waste.getPlastic() + waste.getStyrofoam() + waste.getFiber() + waste.getVinyl() + waste.getGeneralWaste();

        return new wasteResultDto(file.getFileType(), file.getScore(), totalWaste);
    }

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

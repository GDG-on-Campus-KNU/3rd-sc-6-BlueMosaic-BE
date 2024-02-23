package com.gdsc.knu.service;

import com.gdsc.knu.dto.WasteApiResultDto;
import com.gdsc.knu.dto.response.GetImageResponseDto;
import com.gdsc.knu.entity.Waste;
import com.gdsc.knu.repository.WasteRepository;
import com.gdsc.knu.util.ConstVariables;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WasteService {
    private WasteRepository wasteRepository;
    private final GoogleAiService googleAiService;
    private final ConstVariables constVariables;
    private final RankingService rankingService;

    public void processWasteImageAnalysis(GetImageResponseDto getImageResponseDto) {
        String prompt = """
                Requirements :\s
                analyze image and make info with under conditions.
                                
                condition 0. result will be json string.
                condition 1-1. Each trash must have one type. Types : [plastic, styrofoam, fiber, vinyl]
                condition 1-2. If trash is not in the list, it is generalWaste. So result must have 5 types of trash.
                condition 2. Each trash must have number of trashes. Number : [Integer]. ex) 0, 1, 2, 3...

                example result)
                String result = "{
                    "plastic" : 1,
                    "styrofoam" : 0,
                    "fiber" : 6,
                    "vinyl" : 3,
                    "generalWaste" : 1
                }";
                """;
        String apiEndpoint = constVariables.getAPI_URL() + constVariables.getAPI_KEY();

        String response = googleAiService.sendApiRequest(prompt, getImageResponseDto.getBase64EncodedImage(), apiEndpoint);

        WasteApiResultDto wasteApiResultDto = new WasteApiResultDto(googleAiService.parseGoogleApiResponse(response).text);
        int score = calculateWasteScore(getImageResponseDto.getUserId(), wasteApiResultDto);
        rankingService.createRanking(getImageResponseDto.getUserId(), score);
    }

    public int calculateWasteScore(long userId, WasteApiResultDto wasteApiResultDto) {
        try {
            Waste waste = wasteRepository.findById(userId).orElseGet(Waste::new);
            if (waste.getUserId() == null) {
                waste.setUserId(userId);
            }

            // 각 쓰레기 개수 업데이트
            waste.setPlastic(waste.getPlastic() + wasteApiResultDto.getPlastic());
            waste.setStyrofoam(waste.getStyrofoam() + wasteApiResultDto.getStyrofoam());
            waste.setFiber(waste.getFiber() + wasteApiResultDto.getFiber());
            waste.setVinyl(waste.getVinyl() + wasteApiResultDto.getVinyl());
            waste.setGeneralWaste(waste.getGeneralWaste() + wasteApiResultDto.getGeneralWaste());

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

package com.gdsc.knu.service;


import com.gdsc.knu.dto.MarineApiResultDto;
import com.gdsc.knu.dto.response.GetImageResponseDto;
import com.gdsc.knu.repository.MarineLifeRepository;
import com.gdsc.knu.util.ConstVariables;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarineLifeService {
    private final MarineLifeRepository marineLifeRepository;
    private final GoogleAiService googleAiService;
    private final ConstVariables constVariables;

    public void processMarineImageAnalysis(GetImageResponseDto getImageResponseDto) {
        String prompt = """
                Requirements : analyze the image and provide "type : number" information.\s
                condition 1. Each entity must have type of marine animal. if entity not in Type, then mark as others. Type : []
                condition 2. Each entity must have number of entity. ex) 1, 2, 3...
                condition 3. words in english.
                condition 4. don't include human or inanimate.
                
                ex)
                Estuarine Fish : 31, Menhaden : 24, others : 62
                """;
        String apiEndpoint = constVariables.getAPI_URL() + constVariables.getAPI_KEY();

        String response = googleAiService.sendApiRequest(prompt, getImageResponseDto.getBase64EncodedImage(), apiEndpoint);

        MarineApiResultDto marineApiResultDto = new MarineApiResultDto(googleAiService.parseGoogleApiResponse(response).text);

        // TODO : Implement marine score calculation & other related logic.
    }
}

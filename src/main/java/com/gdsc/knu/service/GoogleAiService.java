package com.gdsc.knu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.knu.dto.request.GoogleAiAnalysisRequestDto;
import com.gdsc.knu.dto.response.ai.GoogleAiAnalysisResponse;
import com.gdsc.knu.dto.response.ai.Part;
import com.gdsc.knu.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GoogleAiService {
    private final RestTemplate restTemplate;

    public Part parseGoogleApiResponse(String responseJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            GoogleAiAnalysisResponse response = objectMapper.readValue(responseJson, GoogleAiAnalysisResponse.class);

            if (!(!response.candidates.isEmpty() && !response.candidates.get(0).content.parts.isEmpty()))
                throw new ResourceNotFoundException("No data found in Google AI API response");

            return response.candidates.get(0).content.parts.get(0);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Invalid Response from Google AI API response" + e.getMessage());
        }
    }

    public String sendApiRequest(String prompt, String base64Image, String apiEndpoint)  {
        GoogleAiAnalysisRequestDto request = new GoogleAiAnalysisRequestDto(prompt, base64Image);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GoogleAiAnalysisRequestDto> entity = new HttpEntity<>(request, headers);

        return restTemplate.postForObject(apiEndpoint, entity, String.class);
    }
}

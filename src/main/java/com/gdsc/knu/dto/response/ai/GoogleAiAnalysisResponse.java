package com.gdsc.knu.dto.response.ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// 응답 전체를 나타내는 클래스
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleAiAnalysisResponse {
    public List<Candidate> candidates;
}


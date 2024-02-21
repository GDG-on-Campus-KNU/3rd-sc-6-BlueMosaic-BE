package com.gdsc.knu.dto.response.ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// "candidates" 배열의 각 항목을 나타내는 클래스
@JsonIgnoreProperties(ignoreUnknown = true)
public
class Candidate {
    public Content content;
}

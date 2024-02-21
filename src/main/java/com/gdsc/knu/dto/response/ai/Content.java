package com.gdsc.knu.dto.response.ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// "content" 객체를 나타내는 클래스
@JsonIgnoreProperties(ignoreUnknown = true)
public
class Content {
    public List<Part> parts;
}

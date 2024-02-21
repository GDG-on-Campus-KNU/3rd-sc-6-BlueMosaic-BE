package com.gdsc.knu.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.knu.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class GoogleApiResultDto {
    private int plastic;
    private int styrofoam;
    private int fiber;
    private int vinyl;
    private int generalWaste;

//    public GoogleApiResultDto(String json) {
//        /**
//         */
//    }
}

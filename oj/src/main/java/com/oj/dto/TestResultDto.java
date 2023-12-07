package com.oj.dto;

import com.oj.service.judge.ErrorType;
import lombok.Data;

@Data
public class TestResultDto {
    private Integer testId;
    private Boolean success;
    private ErrorType error;
    private Long memoryUsageKb;
    private Long timeUsageMs;
    private String output;
}

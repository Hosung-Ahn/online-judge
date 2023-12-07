package com.oj.dto.request;

import com.oj.dto.TestCase;
import lombok.Data;

import java.util.List;

@Data
public class ProblemCreateRequestDto {
    private String title;
    private String description;
    private Integer timeLimitSec;
    private Integer memoryLimitMb;
    private List<TestCase> testCases;
}

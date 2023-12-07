package com.oj.dto;

import lombok.Data;

import java.util.List;

@Data
public class JudgeResultDto {
    private Long problemId;
    List<SingleJudgeResultDto> testResults;

    public JudgeResultDto(Long problemId, List<SingleJudgeResultDto> testResults) {
        this.problemId = problemId;
        this.testResults = testResults;
    }
}

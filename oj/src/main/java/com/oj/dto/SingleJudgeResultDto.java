package com.oj.dto;

import com.oj.service.judge.ErrorType;
import lombok.Data;

@Data
public class SingleJudgeResultDto {
    private Integer testId;
    private Boolean success;
    private ErrorType error;
    private Long memoryUsageKb;
    private Long timeUsageMs;

    public SingleJudgeResultDto(TestResultDto testResultDto) {
        this.testId = testResultDto.getTestId();
        this.success = testResultDto.getSuccess();
        this.error = testResultDto.getError();
        this.memoryUsageKb = testResultDto.getMemoryUsageKb();
        this.timeUsageMs = testResultDto.getTimeUsageMs();
    }
}

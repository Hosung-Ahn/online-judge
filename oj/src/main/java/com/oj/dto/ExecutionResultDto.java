package com.oj.dto;

import lombok.Data;

@Data
public class ExecutionResultDto {
    String error;
    int executionTimeInMs;
    int memoryUsageInKb;
    String output;

    public ExecutionResultDto(String error, int executionTimeInMs, int memoryUsageInKb, String output) {
        this.error = error;
        this.executionTimeInMs = executionTimeInMs;
        this.memoryUsageInKb = memoryUsageInKb;
        this.output = output;
    }
}

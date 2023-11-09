package com.oj.dto;

import lombok.Data;

@Data
public class ExecutionResult {
    JudgeResult judgeResult;
    String output;

    public ExecutionResult(JudgeResult judgeResult, String output) {
        this.judgeResult = judgeResult;
        this.output = output;
    }
}

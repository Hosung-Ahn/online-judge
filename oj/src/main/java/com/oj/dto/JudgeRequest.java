package com.oj.dto;

import lombok.Data;

import java.util.List;

@Data
public class JudgeRequest {
    private String sourceCode;
    private Language language;
    private Integer limitTimeSeconds;
    private Integer limitMemoryMegaBytes;
    private List<TestCase> testCases;

    @Data
    public static class TestCase {
        private String input;
        private String output;

        public TestCase(String input, String output) {
            this.input = input;
            this.output = output;
        }
    }

    public JudgeRequest(String sourceCode, Language language, Integer limitTimeSeconds, Integer limitMemoryMegaBytes, List<TestCase> testCases) {
        this.sourceCode = sourceCode;
        this.language = language;
        this.limitTimeSeconds = limitTimeSeconds;
        this.limitMemoryMegaBytes = limitMemoryMegaBytes;
        this.testCases = testCases;
    }
}

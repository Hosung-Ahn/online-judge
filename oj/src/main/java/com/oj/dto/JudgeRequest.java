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
    }
}

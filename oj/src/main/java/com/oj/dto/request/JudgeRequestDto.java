package com.oj.dto.request;

import lombok.Data;

@Data
public class JudgeRequestDto {
    private Long problemId;
    private String sourceCode;
    private String language;
}

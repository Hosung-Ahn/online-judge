package com.oj.dto;

import lombok.Data;

@Data
public class JudgeResponse {
    private JudgeResult result;
    private String message;

    public JudgeResponse(JudgeResult result, String message) {
        this.result = result;
        this.message = message;
    }
}



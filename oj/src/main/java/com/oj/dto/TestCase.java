package com.oj.dto;

import lombok.Data;

@Data
public class TestCase {
    private String input;
    private String output;

    public TestCase(String input, String output) {
        this.input = input;
        this.output = output;
    }
}

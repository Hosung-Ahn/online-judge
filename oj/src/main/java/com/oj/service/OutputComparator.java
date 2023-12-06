package com.oj.service;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class OutputComparator {
    public boolean compare(String userOutput, String testOutput) {
        List<String> user = Arrays.asList(userOutput.split("\\s +"));
        List<String> test = Arrays.asList(testOutput.split("\\s +"));
        return user.equals(test);
    }
}

package com.oj.service.judge;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OutputComparator {
    public static boolean compare(String userOutput, String testOutput) {
        List<String> resultWords = Arrays.asList(userOutput.split("\\s+"));
        List<String> testCaseWords = Arrays.asList(testOutput.split("\\s+"));

        if (resultWords.size() != testCaseWords.size()) {
            return false;
        }

        for (int i = 0; i < resultWords.size(); i++) {
            if (!resultWords.get(i).equals(testCaseWords.get(i))) {
                return false;
            }
        }

        return true;
    }
}

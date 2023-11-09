package com.oj.service;
;
import com.oj.dto.JudgeRequest;
import com.oj.dto.JudgeResponse;
import com.oj.dto.JudgeResult;
import com.oj.dto.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JudgeServiceTest {
    @Autowired
    private JudgeService judgeService;

    @Test
    public void pythonRightAnswerTest() {
        String pythonCode = "a = int(input())\n" +
                "b = int(input())\n" +
                "print(a + b)";
        Language language = Language.PYTHON;
        int timeLimit = 1;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "3"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(pythonCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.SUCCESS);
    }

    @Test
    public void pythonWrongAnswerTest() {
        String pythonCode = "a = int(input())\n" +
                "b = int(input())\n" +
                "print(a + b)";
        Language language = Language.PYTHON;
        int timeLimit = 1;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "2"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(pythonCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void pythonTimeLimitExceededTest() {
        String pythonCode = "a = int(input())\n" +
                "b = int(input())\n" +
                "while True:\n" +
                "    print(a + b)";
        Language language = Language.PYTHON;
        int timeLimit = 1;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "3"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(pythonCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void pythonMemoryLimitExceededTest() {
        String pythonCode = "a = int(input())\n" +
                "b = int(input())\n" +
                "arr = [0] * 10000000\n" +
                "print(a + b)";
        Language language = Language.PYTHON;
        int timeLimit = 1;
        int memoryLimit = 10;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "3"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(pythonCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void pythonSyntaxErrorTest() {
        String pythonCode = "a = int(input())\n" +
                "b = int(input())\n" +
                "print(a + b";
        Language language = Language.PYTHON;
        int timeLimit = 1;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "3"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(pythonCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }
}
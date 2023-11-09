package com.oj.service;

import com.oj.dto.JudgeRequest;
import com.oj.dto.JudgeResponse;
import com.oj.dto.JudgeResult;
import com.oj.dto.Language;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class JavaScriptJudgeTest {
    @Autowired
    private JudgeService judgeService;

    @Test
    public void javaScriptRightAnswerTest() {
        String javaScriptCode =
                "const fs = require('fs');" +
                "const input = fs.readFileSync('/dev/stdin').toString().split('\\n');" +
                "const a = parseInt(input[0], 10);" +
                "const b = parseInt(input[1], 10);" +
                "console.log(a + b);";
        Language language = Language.JAVASCRIPT;
        int timeLimit = 2;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "3"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(javaScriptCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.SUCCESS);
    }

    @Test
    public void javaScriptWrongAnswerTest() {
        String javaScriptCode =
                        "const fs = require('fs');" +
                        "const input = fs.readFileSync('/dev/stdin').toString().split('\\n');" +
                        "const a = parseInt(input[0], 10);" +
                        "const b = parseInt(input[1], 10);" +
                        "console.log(a + b);";
        Language language = Language.JAVASCRIPT;
        int timeLimit = 2;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "2"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(javaScriptCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void javaScriptTimeLimitExceededTest() {
        String javaScriptCode =
                "const fs = require('fs');" +
                "const input = fs.readFileSync('/dev/stdin').toString().split('\\n');" +
                "const a = parseInt(input[0], 10);" +
                "const b = parseInt(input[1], 10);" +
                "while (true) {" +
                "   console.log(a + b);" +
                "}";

        Language language = Language.JAVASCRIPT;
        int timeLimit = 2;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "3"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(javaScriptCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void javaScriptMemoryLimitExceedTest() {
        String javaScriptCode = "const fs = require('fs');" +
                "const input = fs.readFileSync('/dev/stdin').toString().split('\\n');" +
                "const a = parseInt(input[0], 10);" +
                "const b = parseInt(input[1], 10);" +
                "const arr = new Array(100000000).fill(0);" +
                "console.log(a + b);";
        Language language = Language.JAVASCRIPT;
        int timeLimit = 2;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "3"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(javaScriptCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void javaScriptCompileErrorTest() {
        String javaScriptCode = "const fs = require('fs');" +
                "const input = fs.readFileSync('/dev/stdin').toString().split('\\n');" +
                "const a = parseInt(input[0], 10);" +
                "const b = parseInt(input[1], 10);" +
                "console.log(a + b);";
        Language language = Language.JAVA;
        int timeLimit = 2;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "3"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(javaScriptCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void javaScriptRuntimeErrorTest() {
        // 일부러 정의되지 않은 함수를 호출하여 런타임 에러를 유발합니다.
        String javaScriptCode = "const fs = require('fs');" +
                "const input = fs.readFileSync('/dev/stdin').toString().split('\\n');" +
                "const a = parseInt(input[0], 10);" +
                "const b = parseInt(input[1], 10);" +
                "console.log(a / nonExistentFunction(b));"; // nonExistentFunction은 정의되지 않았으므로 런타임 에러 발생

        Language language = Language.JAVASCRIPT;

        int timeLimit = 2;
        int memoryLimit = 128;
        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "0"),
                new JudgeRequest.TestCase("3\n0", ""), // 여기서 런타임 에러 발생 예상
                new JudgeRequest.TestCase("5\n6", "0")
        );

        JudgeRequest judgeRequest = new JudgeRequest(javaScriptCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);

        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }
}

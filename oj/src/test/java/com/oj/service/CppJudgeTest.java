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
public class CppJudgeTest {
    @Autowired
    private JudgeService judgeService;

    @Test
    public void cppRightAnswerTest() {
        String cppCode = "#include <iostream>\n" +
                "int main() {\n" +
                "    int a, b;\n" +
                "    std::cin >> a >> b;\n" +
                "    std::cout << a + b << std::endl;\n" +
                "    return 0;\n" +
                "}";
        Language language = Language.CPP;
        int timeLimit = 2;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1 2", "3"),
                new JudgeRequest.TestCase("3 4", "7"),
                new JudgeRequest.TestCase("5 6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(cppCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.SUCCESS);
    }

    @Test
    public void CppWrongAnswerTest() {
        String cppCode = "#include <iostream>\n" +
                "int main() {\n" +
                "    int a, b;\n" +
                "    std::cin >> a >> b;\n" +
                "    std::cout << a + b << std::endl;\n" +
                "    return 0;\n" +
                "}";
        Language language = Language.CPP;
        int timeLimit = 2;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1 2", "2"),
                new JudgeRequest.TestCase("3 4", "7"),
                new JudgeRequest.TestCase("5 6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(cppCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void cppTimeLimitExceededTest() {
        String cppCode = "#include <iostream>\n" +
                "int main() {\n" +
                "    int a, b;\n" +
                "    std::cin >> a >> b;\n" +
                "    while (true) {\n" +
                "        continue;\n" +
                "    }\n" +
                "    return 0;\n" +
                "}";
        Language language = Language.CPP;
        int timeLimit = 2;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1 2", "3"),
                new JudgeRequest.TestCase("3 4", "7"),
                new JudgeRequest.TestCase("5 6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(cppCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void cppMemoryLimitExceededTest() {
        String cppCode = "#include <iostream>\n" +
                "#include <vector>\n" +
                "int main() {\n" +
                "    std::vector<int> v;\n" +
                "    for (int i=0;i<1000000000;i++) {\n" +
                "        v.push_back(1);\n" +
                "    }\n" +
                "    return 0;\n" +
                "}";
        Language language = Language.CPP;
        int timeLimit = 2;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1 2", "3"),
                new JudgeRequest.TestCase("3 4", "7"),
                new JudgeRequest.TestCase("5 6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(cppCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void cppCompileErrorTest() {
        String cppCode = "#include <iostream>\n" +
                "int main() {\n" +
                "    int a, b;\n" +
                "    std::cin >> a >> b;\n" +
                "    std::cout << a + b << std::endl\n" +
                "    return 0;\n" +
                "}";
        Language language = Language.CPP;
        int timeLimit = 2;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1 2", "3"),
                new JudgeRequest.TestCase("3 4", "7"),
                new JudgeRequest.TestCase("5 6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(cppCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    public void cppRuntimeErrorTest() {
        String cppCode = "#include <iostream>\n" +
                "int main() {\n" +
                "    int a, b;\n" +
                "    std::cin >> a >> b;\n" +
                "    std::cout << a / b << std::endl;\n" +
                "    return 0;\n" +
                "}";
        Language language = Language.CPP;
        int timeLimit = 2;
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1 2", "0"),
                new JudgeRequest.TestCase("3 4", "0"),
                new JudgeRequest.TestCase("5 6", "0")
        );

        JudgeRequest judgeRequest = new JudgeRequest(cppCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }
}

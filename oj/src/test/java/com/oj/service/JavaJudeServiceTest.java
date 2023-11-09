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
public class JavaJudeServiceTest {
    @Autowired
    private JudgeService judgeService;

    @Test
    public void javaRightAnswerTest() {
        String javaCode = "import java.util.Scanner;" +
                "public class Main {" +
                "   public static void main(String[] args) {" +
                "       Scanner scanner = new Scanner(System.in);" +
                "       int a = scanner.nextInt();" +
                "       int b = scanner.nextInt();" +
                "       System.out.println(a + b);" +
                "   }" +
                "}";
        Language language = Language.JAVA;
        int timeLimit = 2; // Java 코드는 컴파일 시간도 포함되므로 시간 제한을 약간 늘려줍니다.
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "3"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(javaCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.SUCCESS);
    }

    @Test
    public void javaWrongAnswerTest() {
        String javaCode = "import java.util.Scanner;" +
                "public class Main {" +
                "   public static void main(String[] args) {" +
                "       Scanner scanner = new Scanner(System.in);" +
                "       int a = scanner.nextInt();" +
                "       int b = scanner.nextInt();" +
                "       System.out.println(a + b);" +
                "   }" +
                "}";
        Language language = Language.JAVA;
        int timeLimit = 2; // Java 코드는 컴파일 시간도 포함되므로 시간 제한을 약간 늘려줍니다.
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "2"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(javaCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void javaTimeLimitExceededTest() {
        String javaCode = "import java.util.Scanner;" +
                "public class Main {" +
                "   public static void main(String[] args) {" +
                "       Scanner scanner = new Scanner(System.in);" +
                "       int a = scanner.nextInt();" +
                "       int b = scanner.nextInt();" +
                "       while (true) {" +
                "           System.out.println(a + b);" +
                "       }" +
                "   }" +
                "}";
        Language language = Language.JAVA;
        int timeLimit = 2; // Java 코드는 컴파일 시간도 포함되므로 시간 제한을 약간 늘려줍니다.
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "3"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(javaCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void javaMemoryLimitExceedTest() {
        String javaCode = "import java.util.Scanner;" +
                "public class Main {" +
                "   public static void main(String[] args) {" +
                "       Scanner scanner = new Scanner(System.in);" +
                "       int a = scanner.nextInt();" +
                "       int b = scanner.nextInt();" +
                "       int[] arr = new int[1024 * 1024 * 1024];" +
                "       System.out.println(a + b);" +
                "   }" +
                "}";
        Language language = Language.JAVA;
        int timeLimit = 2; // Java 코드는 컴파일 시간도 포함되므로 시간 제한을 약간 늘려줍니다.
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "3"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(javaCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void javaCompileErrorTest() {
        String javaCode = "import java.util.Scanner;" +
                "public class Main {" +
                "   public static void main(String[] args) {" +
                "       Scanner scanner = new Scanner(System.in);" +
                "       int a = scanner.nextInt();" +
                "       int b = scanner.nextInt();" +
                "       System.out.println(a + b)" +
                "   }" +
                "}";
        Language language = Language.JAVA;
        int timeLimit = 2; // Java 코드는 컴파일 시간도 포함되므로 시간 제한을 약간 늘려줍니다.
        int memoryLimit = 128;

        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "3"),
                new JudgeRequest.TestCase("3\n4", "7"),
                new JudgeRequest.TestCase("5\n6", "11")
        );

        JudgeRequest judgeRequest = new JudgeRequest(javaCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);
        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }

    @Test
    public void javaRuntimeErrorTest() {
        String javaCode = "import java.util.Scanner;" +
                "public class Main {" +
                "   public static void main(String[] args) {" +
                "       Scanner scanner = new Scanner(System.in);" +
                "       int a = scanner.nextInt();" +
                "       int b = scanner.nextInt();" +
                "       System.out.println(a / b);" +
                "   }" +
                "}";
        Language language = Language.JAVA;

        int timeLimit = 2; // Java 코드는 컴파일 시간도 포함되므로 시간 제한을 약간 늘려줍니다.
        int memoryLimit = 128;
        List<JudgeRequest.TestCase> testCases = List.of(
                new JudgeRequest.TestCase("1\n2", "0"),
                new JudgeRequest.TestCase("3\n0", ""), // 여기서 런타임 에러 발생 예상
                new JudgeRequest.TestCase("5\n6", "0")
        );

        JudgeRequest judgeRequest = new JudgeRequest(javaCode, language, timeLimit, memoryLimit, testCases);
        JudgeResponse result = judgeService.judge(judgeRequest);

        System.out.println(result.getMessage());
        assertThat(result.getResult()).isEqualTo(JudgeResult.FAIL);
    }
}

package com.oj.service;

import com.oj.dto.ExecutionResult;
import com.oj.dto.JudgeResult;
import com.oj.dto.Language;
import com.oj.exception.FileExecutionException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ExecutionByDockerService {
    private static final String FILENAME_IN_CONTAINER = "Main";

    public ExecutionResult run(File sourceFile, File inputFile, Language language,
                               int timeLimit, int memoryLimit) {
        String dockerImage = getDockerImage(language);

        // console 에 입력할 명령어
        String command = getCommand(timeLimit, language);

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(
                "docker", "run", "--rm",
                "--memory", memoryLimit + "m",
                "-v", sourceFile.getAbsolutePath() + ":/tmp/" + FILENAME_IN_CONTAINER + language.getExtension(),
                "-v", inputFile.getAbsolutePath() + ":/tmp/input.txt",
                dockerImage,
                "/bin/sh", "-c",
                command
        );

        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // 프로세스가 종료될 때까지 대기
            int exitCode = process.waitFor();

            // 종료 코드에 따른 처리
            switch (exitCode) {
                case 0:
                    return new ExecutionResult(JudgeResult.SUCCESS, output.toString());
                case 137: // Docker OOM Killer exit code
                    return new ExecutionResult(JudgeResult.FAIL, "에러 : 메모리 초과");
                case 124: // Timeout command exit code for time limit exceeded
                    return new ExecutionResult(JudgeResult.FAIL, "에러 : 시간초과");
                default:
                    return new ExecutionResult(JudgeResult.FAIL, output.toString());
            }
        } catch (IOException e) {
            throw new FileExecutionException("컨테이너 내에서 명령어가 정상적으로 실행되지 않았습니다.");
        } catch (InterruptedException e) {
            throw new RuntimeException("컨테이너 내에서 명령어가 정상적으로 종료되지 않았습니다.");
        }
    }

    private String getDockerImage(Language language) {
        switch (language) {
            case C:
                return "gcc";
            case CPP:
                return "gcc";
            case JAVASCRIPT:
                return "node";
            case JAVA:
                return "openjdk";
            case PYTHON:
                return "python:3.9-slim";
        }
        throw new IllegalArgumentException("Unsupported language");
    }

    private String getCommand(int timeLimit, Language language) {
        switch (language) {
            case C:
                return "";
            case CPP:
                return String.format("g++ /tmp/%s.cpp -o /tmp/%s && timeout --preserve-status -sKILL %ds /tmp/%s < /tmp/input.txt",
                        FILENAME_IN_CONTAINER, FILENAME_IN_CONTAINER, timeLimit, FILENAME_IN_CONTAINER);
            case JAVASCRIPT:
                return String.format("timeout %ds node /tmp/%s.js < /tmp/input.txt", timeLimit, FILENAME_IN_CONTAINER);
            case JAVA:
                return String.format("javac /tmp/%s.java && timeout --preserve-status -sKILL %ds java -cp /tmp %s < /tmp/input.txt",
                        FILENAME_IN_CONTAINER, timeLimit, FILENAME_IN_CONTAINER);
            case PYTHON:
                return String.format("timeout %ds python /tmp/%s.py < /tmp/input.txt", timeLimit, FILENAME_IN_CONTAINER);
            default:
                throw new IllegalArgumentException("Unsupported language");
        }
    }
}

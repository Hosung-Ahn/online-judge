package com.oj.service.judge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oj.dto.TestResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestRunner {
    private final String DOCKER_IMAGE = "runner";

    public TestResultDto runTest(String sourceCodeAbsolutePath, String testInputAbsolutePath,
                                 Language language, int timeLimitSec, int memoryLimitMb) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        processBuilder.command(
                "docker", "run", "--rm",
                "--memory", memoryLimitMb + "m",
                "-v", sourceCodeAbsolutePath + ":/app/" + "Main" + language.getExtension() + ":ro",
                "-v", testInputAbsolutePath + ":/app/input.txt" + ":ro",
                DOCKER_IMAGE,
                "./" + language.getRunner(), timeLimitSec + ""
        );

        try {
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            StringBuilder output = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitCode = process.waitFor();
            System.out.println(output);
            return objectMapper.readValue(output.toString(), TestResultDto.class);
        } catch (Exception e) {
            log.error("Failed to execute docker command", e);
            throw new RuntimeException("Failed to execute test Runner");
        }
    }
}

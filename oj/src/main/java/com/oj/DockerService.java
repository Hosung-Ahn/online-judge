package com.oj;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Service
public class DockerService {


    private static final int MEMORY_LIMIT_MB = 256; // 메모리 제한 256MB
    private static final int TIMEOUT_SECONDS = 2; // 시간 제한 2초


    private String getDockerImage(Language language) {
        switch (language) {
            case C :
                return "gcc";
            case CPP :
                return "gcc";
            case JAVA :
                return "openjdk";
            case PYTHON :
                return "python";
        }
        return "unknown";
    }



    public void runCodeInDocker(File codeFile, Language language) {
        String dockerImage = getDockerImage(language);
        if ("unknown".equals(dockerImage)) {
            System.out.println("Unsupported language");
            return;
        }

        String filePath = codeFile.getAbsolutePath();
        String fileName = codeFile.getName();
        String command = "";

        if (language == Language.PYTHON) {
            command = String.format("timeout %ds python /tmp/%s", TIMEOUT_SECONDS, fileName);
        } else if (language == Language.JAVA) {
            String className = fileName.replaceAll(".java$", "");
            command = String.format("javac /tmp/%s.java && timeout --preserve-status -sKILL %ds java -cp /tmp %s",
                    className, TIMEOUT_SECONDS, className);
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(
                "docker", "run", "--rm",
                "--memory", MEMORY_LIMIT_MB + "m",
                "-v", filePath + ":/tmp/" + fileName,
                dockerImage,
                "/bin/sh", "-c",
                command
        );

        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            // 프로세스 종료 대기
            int exitCode = process.waitFor();

            // 종료 코드에 따른 처리
            switch (exitCode) {
                case 0:
                    System.out.println("Success: program executed successfully.");
                    System.out.println(output);
                    break;
                case 137: // Docker OOM Killer exit code
                    System.out.println("Error: Memory limit exceeded.");
                    break;
                case 124: // Timeout command exit code for time limit exceeded
                    System.out.println("Error: Time limit exceeded.");
                    break;
                default:
                    System.out.println("Error: program execution failed with exit code " + exitCode);
                    System.out.println(output);
                    break;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while running the Docker command: " + e.getMessage());
        }
    }
}

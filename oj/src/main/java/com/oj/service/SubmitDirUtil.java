package com.oj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class SubmitDirUtil {
    private static final String CURRENT_DIRECTORY = System.getProperty("user.dir");
    private static final String USER_SUBMIT_DIR = "src/main/java/com/oj/user_submit";

    public File createSubmitDir(String submitId) {
        Path submitPath = Path.of(CURRENT_DIRECTORY, USER_SUBMIT_DIR);
        createDir(submitPath);
        Path UsersubmitDirPath = Path.of(CURRENT_DIRECTORY, USER_SUBMIT_DIR, submitId);
        return createDir(UsersubmitDirPath);
    }

    private File createDir(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                String message = "Failed to create directory : " + path;
                log.error(message);
                throw new RuntimeException(message);
            }
        } else {
            log.info("Directory already exists : " + path);
        }
        return path.toFile();
    }

    public File createTempSourceFile(String sourceCode, Language language, String submitId) {
        Path submitDirPath = Path.of(CURRENT_DIRECTORY, USER_SUBMIT_DIR, submitId);
        File tempSourceFile = null;
        try {
            tempSourceFile = File.createTempFile("Main", language.getExtension(), submitDirPath.toFile());
        } catch (IOException e) {
            String message = "Failed to create temp file";
            log.error(message);
            throw new RuntimeException(message);
        }

        try {
            Files.write(tempSourceFile.toPath(), sourceCode.getBytes());
        } catch (IOException e) {
            String message = "Failed to write source code to temp file";
            log.error(message);
            throw new RuntimeException(message);
        }
        return tempSourceFile;
    }

    public File createTempTextFile(String inputText, String submitId) {
        Path submitDirPath = Path.of(CURRENT_DIRECTORY, USER_SUBMIT_DIR, submitId);
        File tempTextFile = null;
        try {
            tempTextFile = File.createTempFile("input", ".txt", submitDirPath.toFile());
        } catch (IOException e) {
            String message = "Failed to create temp file";
            log.error(message);
            throw new RuntimeException(message);
        }
        try {
            Files.write(tempTextFile.toPath(), inputText.getBytes());
        } catch (IOException e) {
            String message = "Failed to write input text to temp file";
            log.error(message);
            throw new RuntimeException(message);
        }
        return tempTextFile;
    }
}

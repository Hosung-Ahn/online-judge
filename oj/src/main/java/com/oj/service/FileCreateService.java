package com.oj.service;

import com.oj.dto.Language;
import com.oj.exception.FileCreateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class FileCreateService {
    private static final String USER_SUBMIT_DIR = "src/main/java/com/oj/user_submit";
    private static final String CURRENT_WORKING_DIR = System.getProperty("user.dir");
    public File createTempSourceFile(String sourceCode, Language language) {
        Path submitDirPath = Path.of(CURRENT_WORKING_DIR, USER_SUBMIT_DIR);

        // submitDirPath 에 해당하는 디렉토리가 없으면 생성합니다.
        createDir(submitDirPath);

        // sourceCode 를 일시적으로 저장할 파일을 생성합니다.
        File tempFile = null;
        try {
            // createTempFile 은 랜덤한 이름의 파일을 생성합니다. -> 동시성 이슈를 막아줍니다.
            tempFile = File.createTempFile("Main", language.getExtension(), submitDirPath.toFile());
        } catch (IOException e) {
            String message = "Failed to create temp file";
            log.error(message);
            throw new FileCreateException(message);
        }

        // 생성한 파일에 sourceCode 를 작성합니다.
        try {
            Files.write(tempFile.toPath(), sourceCode.getBytes());
            log.info("Created temp file: " + tempFile.getName());
        } catch (IOException e) {
            String message = "Failed to write source code to temp file";
            log.error(message);
            throw new FileCreateException(message);
        }

        return tempFile;
    }

    public File createTempTextFile(String inputText) {
        Path submitDirPath = Path.of(CURRENT_WORKING_DIR, USER_SUBMIT_DIR);
 
        // submitDirPath 에 해당하는 디렉토리가 없으면 생성합니다.
        createDir(submitDirPath);

        // inputText 를 일시적으로 저장할 파일을 생성합니다.
        File tempFile = null;
        try {
            // createTempFile 은 랜덤한 이름의 파일을 생성합니다. -> 동시성 이슈를 막아줍니다.
            tempFile = File.createTempFile("input", ".txt", submitDirPath.toFile());
        } catch (IOException e) {
            String message = "Failed to create temp file";
            log.error(message);
            throw new FileCreateException(message);
        }

        // 생성한 파일에 inputText 를 작성합니다.
        try {
            Files.write(tempFile.toPath(), inputText.getBytes());
            log.info("Created temp file: " + tempFile.getName());
        } catch (IOException e) {
            String message = "Failed to write input text to temp file";
            log.error(message);
            throw new FileCreateException(message);
        }

        return tempFile;
    }

    private void createDir(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                log.info("Created directory: " + path);
            } catch (IOException e) {
                String message = "Failed to create directory: " + path;
                log.error(message);
                throw new FileCreateException(message);
            }
        }
    }
}

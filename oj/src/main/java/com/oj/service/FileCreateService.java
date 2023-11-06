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
    public File createTempFile(String sourceCode, Language language) {
        String currentWorkingDir = System.getProperty("user.dir");
        Path submitDirPath = Path.of(currentWorkingDir, USER_SUBMIT_DIR);

        // submitDirPath 에 해당하는 디렉토리가 없으면 생성합니다.
        if (!Files.exists(submitDirPath)) {
            try {
                Files.createDirectories(submitDirPath);
                log.info("Created directory: " + submitDirPath);
            } catch (IOException e) {
                String message = "Failed to create directory: " + submitDirPath;
                log.error(message);
                throw new FileCreateException(message);
            }
        }

        // sourceCode 를 일시적으로 저장할 파일을 생성합니다.
        File tempFile = null;
        try {
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
}

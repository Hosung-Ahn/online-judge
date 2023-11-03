package com.oj;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileService {
    private static final String SUBMIT_DIR = "src/main/java/com/oj/submit";

    public File makeFile(Language language, String sourceCode) {
        String currentWorkingDir = System.getProperty("user.dir");
        Path submitDirPath = Path.of(currentWorkingDir, SUBMIT_DIR);

        // Java의 경우 클래스 이름을 추출합니다.
        String fileName = "Main";
        String suffix = getExtension(language);

        File tempFile = null;
        try {
            // Java 파일의 경우 클래스 이름을 파일 이름으로 사용합니다.
            tempFile = new File(submitDirPath.toFile(), fileName + suffix);
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(sourceCode);
            }
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("파일 생성 실패");
        }
        return tempFile;
    }

    private String getExtension(Language language) {
        switch (language) {
            case C:
                return ".c";
            case CPP:
                return ".cpp";
            case JAVA:
                return ".java";
            case PYTHON:
                return ".py";
            default:
                throw new RuntimeException("Invalid language");
        }
    }
}

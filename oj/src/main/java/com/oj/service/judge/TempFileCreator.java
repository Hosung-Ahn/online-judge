package com.oj.service.judge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TempFileCreator {
    public static File createTempFile(String content, String fileName, String extension, File directory) {
        try {
            File tempFile = File.createTempFile(fileName, extension, directory);
            Path filePath = tempFile.toPath();
            Files.writeString(filePath, content);
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

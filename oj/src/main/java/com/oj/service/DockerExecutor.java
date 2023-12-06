package com.oj.service;

import com.oj.dto.ExecutionResultDto;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DockerExecutor {
    private final String FILE_NAME_IN_CONTAINER = "Main";

    public ExecutionResultDto execute(File sourceCode, File inputText, Language language,
                                      int timeLimitInMs, int memoryLimitInKb) {
        String dockerImage = language.getDockerImage();

    }

    private String getCommend(Language language, int timeLimitInMs, int memoryLimitInKb) {
        return String.format()
    }
}

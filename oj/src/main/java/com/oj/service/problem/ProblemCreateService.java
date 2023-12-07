package com.oj.service.problem;

import com.oj.dto.TestCase;
import com.oj.dto.request.ProblemCreateRequestDto;
import com.oj.entity.Problem;
import com.oj.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemCreateService {
    private final String TEST_CASE_PATH = "/Users/hosung/Workspace/online-judge/oj/src/main/java/com/oj/testcase";

    private final ProblemRepository problemRepository;

    public void createProblem(ProblemCreateRequestDto requestDto) {
        Problem problem = saveProblem(requestDto);
        createTestCases(problem.getId(), requestDto.getTestCases());
    }

    private Problem saveProblem(ProblemCreateRequestDto requestDto) {
        Problem problem = Problem.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .memoryLimitMb(requestDto.getMemoryLimitMb())
                .timeLimitSec(requestDto.getTimeLimitSec())
                .testCount(requestDto.getTestCases().size())
                .build();
        return problemRepository.save(problem);
    }

    private void createTestCases(Long problemId, List<TestCase> testCases) {
        File problemDir = new File(TEST_CASE_PATH, problemId.toString());
        if (!problemDir.exists()) {
            problemDir.mkdir();
        }

        for (int i = 0; i < testCases.size(); i++) {
            TestCase testCase = testCases.get(i);
            createTestCaseFile(problemDir, "input" + i + ".txt", testCase.getInput());
            createTestCaseFile(problemDir, "output" + i + ".txt", testCase.getOutput());
        }
    }

    private void createTestCaseFile(File directory, String fileName, String content) {
        Path filePath = Path.of(directory.getAbsolutePath(), fileName);
        try {
            Files.writeString(filePath, content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create test case file", e);
        }
    }
}

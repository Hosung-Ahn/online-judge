package com.oj.service.judge;

import com.oj.dto.SingleJudgeResultDto;
import com.oj.dto.TestResultDto;
import com.oj.entity.Problem;
import com.oj.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JudgeService {
    private final String testCasePath = "/Users/hosung/Workspace/online-judge/oj/src/main/java/com/oj/testcase";
    private final String sourceCodePath = "/Users/hosung/Workspace/online-judge/oj/src/main/java/com/oj/sourcecode";

    private final ProblemRepository problemRepository;
    private final TestRunner testRunner;


    public List<SingleJudgeResultDto> judge(Long problemId, String sourceCode , Language language) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(
                () -> new IllegalArgumentException("Problem not found"));
        Integer testCount = problem.getTestCount();
        Integer timeLimitSec = problem.getTimeLimitSec();
        Integer memoryLimitMb = problem.getMemoryLimitMb();
        File tempSourceFile = TempFileCreator.createTempFile(
                sourceCode, "source", language.getExtension(), new File(sourceCodePath));

        List<SingleJudgeResultDto> testResults = new ArrayList<>();

        for (int i=0; i<testCount; i++) {
            String inputTestPath = testCasePath + "/" + problemId + "/" + "input" + i + ".txt";
            String outputTestPath = testCasePath + "/" + problemId + "/" + "output" + i + ".txt";

            TestResultDto testResultDto = testRunner.runTest(tempSourceFile.getAbsolutePath(), inputTestPath,
                    language, timeLimitSec, memoryLimitMb);
            testResultDto.setTestId(i);

            if (!testResultDto.getSuccess()) {
                testResults.add(new SingleJudgeResultDto(testResultDto));
            } else {
                String output = getStringFromFile(outputTestPath);
                testResults.add(getComparedResult(testResultDto, output));
            }
        }
        tempSourceFile.delete();
        return testResults;
    }

    private SingleJudgeResultDto getComparedResult(TestResultDto testResultDto, String output) {
        if (!OutputComparator.compare(testResultDto.getOutput(), output)) {
            testResultDto.setSuccess(false);
            testResultDto.setError(ErrorType.WRONG_ANSWER);
        }
        return new SingleJudgeResultDto(testResultDto);
    }

    private String getStringFromFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

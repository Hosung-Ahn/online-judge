package com.oj.service;

import com.oj.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JudgeService {
    private final FileCreateService fileCreateService;
    private final ExecutionByDockerService executionByDockerService;
    private final OutputComparator outputComparator;

    public JudgeResponse judge(JudgeRequest judgeRequest) {
        String sourceCode = judgeRequest.getSourceCode();
        Language language = judgeRequest.getLanguage();
        int timeLimit = judgeRequest.getLimitTimeSeconds();
        int memoryLimit = judgeRequest.getLimitMemoryMegaBytes();
        List<JudgeRequest.TestCase> testCases = judgeRequest.getTestCases();

        File sourceFile = fileCreateService.createTempSourceFile(sourceCode, language);
        for (JudgeRequest.TestCase testCase : testCases) {
            File inputFile = fileCreateService.createTempTextFile(testCase.getInput());

            ExecutionResult result = executionByDockerService.run(
                    sourceFile, inputFile, language, timeLimit, memoryLimit);

            // 파일 삭제
            inputFile.delete();

            // 메모리 초과, 시간 초과, 런타임 에러 등으로 파일이 실행되지 않은 경우
            if (result.getJudgeResult() == JudgeResult.FAIL) {
                return new JudgeResponse(result.getJudgeResult(), result.getOutput());
            }
            // 파일이 실행되어 결과물을 출력했으나 테스트 케이스와 다른 경우
            if (!outputComparator.compare(result.getOutput(), testCase.getOutput())) {
                return new JudgeResponse(JudgeResult.FAIL, "틀렸습니다.");
            }
            // 테스트 케이스 하나에 대해서 통과했으므로 다음 for 문으로 넘어가 다음 코드와 비교
        }

        // 파일 삭제
        sourceFile.delete();

        // 모든 테스트 케이스를 통과했으므로 성공
        return new JudgeResponse(JudgeResult.SUCCESS, "맞았습니다!!");
    }
}

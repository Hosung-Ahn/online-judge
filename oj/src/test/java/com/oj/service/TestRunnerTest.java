package com.oj.service;

import com.oj.dto.TestResultDto;
import com.oj.service.judge.Language;
import com.oj.service.judge.TestRunner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class TestRunnerTest {
    @Autowired
    private TestRunner testRunner;

    @Test
    public void runTest(){
//        File testInput = new File("/Users/hosung/Workspace/online-judge/oj/src/main/java/com/oj/testcase/sample/input.txt");
//        File sourceCode = new File("/Users/hosung/Workspace/online-judge/oj/src/main/java/com/oj/sourcecode/Sample.py");
//        TestResultDto testResultDto = testRunner.runTest(sourceCode, testInput, Language.PYTHON, 1, 128);
//        System.out.println(testResultDto);
    }
}
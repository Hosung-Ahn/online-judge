package com.oj.service.problem;

import com.oj.dto.TestCase;
import com.oj.dto.request.ProblemCreateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProblemCreateServiceTest {
    @Autowired
    private ProblemCreateService problemCreateService;

    @Test
    void problemCreateTest() {
        ProblemCreateRequestDto requestDto = new ProblemCreateRequestDto();
        List<TestCase> testCases = List.of(
                new TestCase("1 2", "3"),
                new TestCase("3 4", "7"),
                new TestCase("5 6", "11")
        );

        requestDto.setTitle("A+B");
        requestDto.setDescription("두 정수 A와 B를 입력받은 다음, A+B를 출력하는 프로그램을 작성하시오.");
        requestDto.setTimeLimitSec(1);
        requestDto.setMemoryLimitMb(128);
        requestDto.setTestCases(testCases);

        problemCreateService.createProblem(requestDto);
    }
}
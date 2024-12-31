package com.example.ojserver.service

import com.example.ojserver.dto.ProblemCreateRequestDto
import com.example.ojserver.dto.ProblemResponseDto
import com.example.ojserver.dto.TestCaseDto
import com.example.ojserver.entity.Problem
import com.example.ojserver.entity.TestCase
import com.example.ojserver.repository.ProblemRepository
import com.example.ojserver.repository.TestCaseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProblemService(
    private val problemRepository: ProblemRepository,
    private val testCaseRepository: TestCaseRepository,
) {
    fun createProblem(problemCreateRequestDto: ProblemCreateRequestDto): Long {
        val problem =
            problemRepository.save(
                Problem(
                    title = problemCreateRequestDto.title,
                    content = problemCreateRequestDto.content,
                    timeLimit = problemCreateRequestDto.timeLimit,
                    memoryLimit = problemCreateRequestDto.memoryLimit,
                ),
            )

        problemCreateRequestDto.testCases.forEach {
            testCaseRepository.save(
                TestCase(
                    input = it.input,
                    output = it.output,
                    problem = problem,
                ),
            )
        }

        return problem.id
    }

    fun getProblem(problemId: Long): ProblemResponseDto {
        val problem =
            problemRepository.findById(problemId).orElseThrow {
                IllegalArgumentException("Problem not found")
            }
        return ProblemResponseDto(
            title = problem.title,
            content = problem.content,
            testCases =
                problem.testCases.map {
                    TestCaseDto(
                        input = it.input,
                        output = it.output,
                    )
                },
            timeLimit = problem.timeLimit,
            memoryLimit = problem.memoryLimit,
        )
    }
}

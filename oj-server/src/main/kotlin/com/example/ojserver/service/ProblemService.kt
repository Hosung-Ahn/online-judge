package com.example.ojserver.service

import com.example.ojserver.dto.ProblemCreateRequestDto
import com.example.ojserver.entity.Problem
import com.example.ojserver.entity.TestCase
import com.example.ojserver.repository.ProblemRepository
import com.example.ojserver.repository.TestCaseRepository
import org.springframework.stereotype.Service

@Service
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
}

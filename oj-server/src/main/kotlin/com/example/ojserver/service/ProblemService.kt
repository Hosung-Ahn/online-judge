package com.example.ojserver.service

import com.example.ojserver.dto.ProblemCreateRequestDto
import com.example.ojserver.dto.ProblemResponseDto
import com.example.ojserver.dto.TestCaseDto
import com.example.ojserver.entity.Problem
import com.example.ojserver.repository.ProblemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProblemService(
    private val problemRepository: ProblemRepository,
    private val fileService: FileService,
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
        return problem.id
    }

    fun addTestCase(testCaseDto: TestCaseDto) {
        val problem =
            problemRepository.findById(testCaseDto.problemId).orElseThrow {
                IllegalArgumentException("Problem not found")
            }
        problem.testCaseCount += 1
        fileService.saveFile(testCaseDto.input, "${testCaseDto.problemId}_${testCaseDto.caseId}_input.txt")
        fileService.saveFile(testCaseDto.output, "${testCaseDto.problemId}_${testCaseDto.caseId}_output.txt")
    }

    fun getProblem(problemId: Long): ProblemResponseDto =
        problemRepository
            .findById(problemId)
            .orElseThrow {
                IllegalArgumentException("Problem not found")
            }.let {
                ProblemResponseDto(
                    title = it.title,
                    content = it.content,
                    timeLimit = it.timeLimit,
                    memoryLimit = it.memoryLimit,
                    testCaseCount = it.testCaseCount,
                )
            }
}

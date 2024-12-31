package com.example.ojserver.controller

import com.example.ojserver.dto.ProblemCreateRequestDto
import com.example.ojserver.dto.ProblemCreateResponseDto
import com.example.ojserver.dto.ProblemResponseDto
import com.example.ojserver.dto.TestCaseDto
import com.example.ojserver.service.ProblemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ProblemController(
    private val problemService: ProblemService,
) {
    @PostMapping("/problem")
    fun createProblem(
        @RequestBody problemCreateRequestDto: ProblemCreateRequestDto,
    ): ResponseEntity<ProblemCreateResponseDto> {
        val problemId = problemService.createProblem(problemCreateRequestDto)
        return ResponseEntity.ok(ProblemCreateResponseDto(problemId))
    }

    @GetMapping("/problem/{problemId}")
    fun getProblem(
        @PathVariable problemId: Long,
    ): ResponseEntity<ProblemResponseDto> {
        val problem = problemService.getProblem(problemId)
        return ResponseEntity.ok(problem)
    }

    @GetMapping("/testCase/upload")
    fun uploadTestCase(testCaseDto: TestCaseDto): ResponseEntity<String> {
        problemService.addTestCase(testCaseDto)
        return ResponseEntity.ok("Uploaded")
    }
}

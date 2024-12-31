package com.example.ojserver.dto

import com.example.ojserver.entity.ErrorType
import org.springframework.web.multipart.MultipartFile

data class ProblemCreateRequestDto(
    val title: String,
    val content: String,
    val timeLimit: Int,
    val memoryLimit: Int,
)

data class TestCaseDto(
    val problemId: Long,
    val caseId: Long,
    val input: MultipartFile,
    val output: MultipartFile,
)

data class ProblemCreateResponseDto(
    val problemId: Long,
)

data class ProblemResponseDto(
    val title: String,
    val content: String,
    val timeLimit: Int,
    val memoryLimit: Int,
    val testCaseCount: Int,
)

data class RunResultDto(
    val success: Boolean,
    val error: ErrorType?,
    val timeUsageMs: Long?,
    val memoryUsageKb: Long?,
    val output: String?,
)

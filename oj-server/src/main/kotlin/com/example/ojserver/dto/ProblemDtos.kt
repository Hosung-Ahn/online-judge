package com.example.ojserver.dto

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

package com.example.ojserver.dto

data class ProblemCreateRequestDto(
    val title: String,
    val content: String,
    val testCases: List<TestCaseDto>,
    val timeLimit: Int,
    val memoryLimit: Int,
)

data class TestCaseDto(
    val input: String,
    val output: String,
)

data class ProblemCreateResponseDto(
    val problemId: Long,
)

data class ProblemResponseDto(
    val title: String,
    val content: String,
    val testCases: List<TestCaseDto>,
    val timeLimit: Int,
    val memoryLimit: Int,
)

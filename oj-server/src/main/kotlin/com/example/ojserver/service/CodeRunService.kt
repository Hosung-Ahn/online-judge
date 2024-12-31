package com.example.ojserver.service

import com.example.ojserver.dto.RunResultDto
import com.example.ojserver.entity.Language
import org.springframework.stereotype.Service

@Service
class CodeRunService(
    private val fileService: FileService,
) {
    fun runCode(
        sourceCodePath: String,
        inputPath: String,
        language: Language,
        timeLimitSec: Int,
        memoryLimitMb: Int,
    ): RunResultDto {
        val processBuilder = ProcessBuilder()
        processBuilder.command(
            "docker",
            "run",
            "--rm",
            "--memory",
            memoryLimitMb.toString() + "m",
            "-v",
            sourceCodePath + ":/app/" + "Main" + language.getExtension() + ":ro",
            "-v",
            inputPath + ":/app/input.txt" + ":ro",
            "runner",
            "./" + language.getRunner(),
            timeLimitSec.toString(),
        )
    }
}

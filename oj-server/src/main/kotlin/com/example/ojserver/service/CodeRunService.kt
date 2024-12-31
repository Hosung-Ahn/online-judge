package com.example.ojserver.service

import com.example.ojserver.dto.RunResultDto
import com.example.ojserver.entity.Language
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class CodeRunService(
    private val objectMapper: ObjectMapper,
) {
    fun runCode(
        sourceCodePath: String,
        inputPath: String,
        language: Language,
        timeLimitSec: Int,
        memoryLimitMb: Int,
    ): RunResultDto {
        val command =
            listOf(
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

        return try {
            val process =
                ProcessBuilder(command)
                    .redirectErrorStream(false)
                    .start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            println(output)
            objectMapper.readValue(output, RunResultDto::class.java)
        } catch (e: Exception) {
            throw RuntimeException("Failed to execute test runner", e)
        }
    }
}

package com.example.ojserver.service

import com.example.ojserver.entity.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CodeRunServiceTest(
    @Autowired private val codeRunService: CodeRunService,
    @Autowired private val fileService: FileService,
) {
    @Test
    fun runCode() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = "/Users/hosungan/Workspace/online-judge/oj-server/uploads/1735622353283_Main.cpp"
        val result = codeRunService.runCode(sourcePath, inputPath, Language.CPP, 1, 256)
        println(result)
    }
}

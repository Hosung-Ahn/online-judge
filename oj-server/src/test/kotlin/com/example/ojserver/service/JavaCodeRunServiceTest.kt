package com.example.ojserver.service

import com.example.ojserver.entity.ErrorType
import com.example.ojserver.entity.Language
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JavaCodeRunServiceTest(
    @Autowired private val codeRunService: CodeRunService,
    @Autowired private val fileService: FileService,
) {
    @Test
    fun `Java 코드 작동 성공`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = fileService.getPath("TestSuccess.java")
        val result = codeRunService.runCode(sourcePath, inputPath, Language.JAVA, 1, 256)

        println(result)
        result.output shouldBe "30\n"
        result.success shouldBe true
    }

    @Test
    fun `Java 코드 작동 실패 - 컴파일 에러`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = fileService.getPath("TestCompileError.java")
        val result = codeRunService.runCode(sourcePath, inputPath, Language.JAVA, 1, 256)

        println(result)
        result.success shouldBe false
        result.error shouldBe ErrorType.COMPILE_ERROR
    }

    @Test
    fun `Java 코드 작동 실패 - 런타임 에러`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = fileService.getPath("TestRuntimeError.java")
        val result = codeRunService.runCode(sourcePath, inputPath, Language.JAVA, 1, 256)

        println(result)
        result.success shouldBe false
        result.error shouldBe ErrorType.RUNTIME_ERROR
    }

    @Test
    fun `Java 코드 작동 실패 - 시간 초과`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = fileService.getPath("TestTimeOut.java")
        val result = codeRunService.runCode(sourcePath, inputPath, Language.JAVA, 1, 256)

        println(result)
        result.success shouldBe false
        result.error shouldBe ErrorType.TIMEOUT_ERROR
    }

    @Test
    fun `Java 코드 작동 실패 - 메모리 초과`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = fileService.getPath("TestMemoryOut.java")
        val result = codeRunService.runCode(sourcePath, inputPath, Language.JAVA, 10, 256)

        println(result)
        result.success shouldBe false
        result.error shouldBe ErrorType.MEMORYOUT_ERROR
    }
}

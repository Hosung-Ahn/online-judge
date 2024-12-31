package com.example.ojserver.service

import com.example.ojserver.entity.ErrorType
import com.example.ojserver.entity.Language
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PythonCodeRunServiceTest(
    @Autowired private val codeRunService: CodeRunService,
    @Autowired private val fileService: FileService,
) {
    @Test
    fun `Python 코드 작동 성공`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = fileService.getPath("TestSuccess.py")
        val result = codeRunService.runCode(sourcePath, inputPath, Language.PYTHON, 1, 256)

        println(result)
        result.output shouldBe "30\n"
        result.success shouldBe true
    }

    // 파이썬은 컴파일하지 않으니 컴파일 에러는 생략

    @Test
    fun `Python 코드 작동 실패 - 런타임 에러`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = fileService.getPath("TestRuntimeError.py")
        val result = codeRunService.runCode(sourcePath, inputPath, Language.PYTHON, 1, 256)

        println(result)
        result.success shouldBe false
        result.error shouldBe ErrorType.RUNTIME_ERROR
    }

    @Test
    fun `Python 코드 작동 실패 - 시간 초과`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = fileService.getPath("TestTimeOut.py")
        val result = codeRunService.runCode(sourcePath, inputPath, Language.PYTHON, 1, 256)

        println(result)
        result.success shouldBe false
        result.error shouldBe ErrorType.TIMEOUT_ERROR
    }

    @Test
    fun `Python 코드 작동 실패 - 메모리 초과`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = fileService.getPath("TestMemoryOut.py")
        val result = codeRunService.runCode(sourcePath, inputPath, Language.PYTHON, 10, 256)

        println(result)
        result.success shouldBe false
        result.error shouldBe ErrorType.MEMORYOUT_ERROR
    }
}

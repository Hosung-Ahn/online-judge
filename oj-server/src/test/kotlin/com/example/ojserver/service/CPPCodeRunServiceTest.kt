package com.example.ojserver.service

import com.example.ojserver.entity.ErrorType
import com.example.ojserver.entity.Language
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CPPCodeRunServiceTest(
    @Autowired private val codeRunService: CodeRunService,
    @Autowired private val fileService: FileService,
) {
    @Test
    fun `CPP 코드 작동 성공`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = "/Users/hosungan/Workspace/online-judge/oj-server/uploads/TestSuccess.cpp"
        val result = codeRunService.runCode(sourcePath, inputPath, Language.CPP, 1, 256)

        println(result)
        result.output shouldBe "30\n"
        result.success shouldBe true
    }

    @Test
    fun `CPP 코드 작동 실패 - 컴파일 에러`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = "/Users/hosungan/Workspace/online-judge/oj-server/uploads/TestCompileError.cpp"
        val result = codeRunService.runCode(sourcePath, inputPath, Language.CPP, 1, 256)

        println(result)
        result.success shouldBe false
        result.error shouldBe ErrorType.COMPILE_ERROR
    }

    @Test
    fun `CPP 코드 작동 실패 - 런타임 에러`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = "/Users/hosungan/Workspace/online-judge/oj-server/uploads/TestRuntimeError.cpp"
        val result = codeRunService.runCode(sourcePath, inputPath, Language.CPP, 1, 256)

        println(result)
        result.success shouldBe false
        result.error shouldBe ErrorType.RUNTIME_ERROR
    }

    @Test
    fun `CPP 코드 작동 실패 - 시간 초과`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = "/Users/hosungan/Workspace/online-judge/oj-server/uploads/TestTimeOut.cpp"
        val result = codeRunService.runCode(sourcePath, inputPath, Language.CPP, 1, 256)

        println(result)
        result.success shouldBe false
        result.error shouldBe ErrorType.TIMEOUT_ERROR
    }

    @Test
    fun `CPP 코드 작동 실패 - 메모리 초과`() {
        val inputPath = fileService.getPath("1_1_input.txt")
        val sourcePath = "/Users/hosungan/Workspace/online-judge/oj-server/uploads/TestMemoryOut.cpp"
        val result = codeRunService.runCode(sourcePath, inputPath, Language.CPP, 10, 256)

        println(result)
        result.success shouldBe false
        result.error shouldBe ErrorType.MEMORYOUT_ERROR
    }
}

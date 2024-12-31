package com.example.ojserver.entity

enum class Language {
    CPP {
        override fun getExtension() = ".cpp"

        override fun getRunner() = "cpp_runner.sh"
    },
    JAVA {
        override fun getExtension() = ".java"

        override fun getRunner() = "java_runner.sh"
    },
    PYTHON {
        override fun getExtension() = ".py"

        override fun getRunner() = "python_runner.sh"
    }, ;

    abstract fun getExtension(): String

    abstract fun getRunner(): String
}

enum class ErrorType {
    COMPILE_ERROR,
    RUNTIME_ERROR,
    TIMEOUT_ERROR,
    MEMORYOUT_ERROR,
    WRONG_ANSWER,
}

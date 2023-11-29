#include <iostream>
#include <array>
#include <memory>
#include <chrono>
#include <cstdio>
#include <stdexcept>
#include <sys/resource.h>

class Executor {
public:
    virtual std::string compile(const std::string& sourcePath) = 0;
    virtual std::string execute(const std::string& executablePath) = 0;
    virtual ~Executor() {}
};

class PythonExecutor : public Executor {
public:
    std::string compile(const std::string& sourcePath) override {
        // Python은 인터프리터 언어이기 때문에 컴파일 단계가 필요 없습니다.
        return "";
    }

    std::string execute(const std::string& executablePath) override {
        std::string cmd = "python " + executablePath;
        return exec(cmd.c_str());
    }
};

class CppExecutor : public Executor {
public:
    std::string compile(const std::string& sourcePath) override {
        std::string executablePath = sourcePath + ".out";
        std::string cmd = "g++ " + sourcePath + " -o " + executablePath;
        return exec(cmd.c_str());
    }

    std::string execute(const std::string& executablePath) override {
        return exec(("./" + executablePath).c_str());
    }
};

class JavaExecutor : public Executor {
public:
    std::string compile(const std::string& sourcePath) override {
        std::string cmd = "javac " + sourcePath;
        return exec(cmd.c_str());
    }

    std::string execute(const std::string& executablePath) override {
        std::string className = executablePath.substr(0, executablePath.find('.'));
        std::string cmd = "java " + className;
        return exec(cmd.c_str());
    }
};

class ExecutionManager {
private:
    std::unique_ptr<Executor> executor;

    std::string exec(const char* cmd) {
        std::array<char, 128> buffer;
        std::string result;
        std::unique_ptr<FILE, decltype(&pclose)> pipe(popen(cmd, "r"), pclose);
        if (!pipe) {
            throw std::runtime_error("popen() failed!");
        }
        while (fgets(buffer.data(), buffer.size(), pipe.get()) != nullptr) {
            result += buffer.data();
        }
        return result;
    }

    double getMemoryUsage() {
        struct rusage usage;
        getrusage(RUSAGE_CHILDREN, &usage);
        return usage.ru_maxrss; // Peak resident set size used (in kilobytes)
    }

public:
    ExecutionManager(Executor* exec) : executor(exec) {}

    void run(const std::string& sourcePath) {
        auto startTime = std::chrono::high_resolution_clock::now();
        std::string compileMessage = executor->compile(sourcePath);
        if (!compileMessage.empty()) {
            std::cerr << "Compile Error: " << compileMessage << std::endl;
            return;
        }
        std::string executablePath = (executor->execute(sourcePath)).empty() ? sourcePath : sourcePath + ".out";
        std::string runMessage = executor->execute(executablePath);
        auto endTime = std::chrono::high_resolution_clock::now();
        std::chrono::duration<double> execTime = endTime - startTime;

        if (!runMessage.empty()) {
            std::cerr << "Runtime Error: " << runMessage << std::endl;
        } else {
            std::cout << "Execution Time: " << execTime.count() << " seconds" << std::endl;
            std::cout << "Memory Usage: " << getMemoryUsage() << " KB" << std::endl;
        }
    }
};

int main() {
    // 예시로, PythonExecutor로 실행 매니저를 생성합니다.
    ExecutionManager manager(new PythonExecutor());
    manager.run("script.py"); // Python 스크립트를 실행합니다.

    // C++와 Java에 대해서도 비슷하게 Executor 객체를 생성하고 run 함수를 호출할 수 있습니다.

    return 0;
}

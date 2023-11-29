#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>
#include <cstdio>
#include <sys/wait.h>
#include <unistd.h>

const std::string MAIN_CPP = "Main.cpp";
const std::string INPUT_FILE = "input.txt";
const std::string COMPILE_ERROR = "Compile Error";
const std::string RUNTIME_ERROR = "Runtime Error";
const std::string TIMEOUT_ERROR = "Timeout Error";

void createResult(bool success, const std::string &error, int executionTimeInMs, const std::string &output) {
    std::cout << std::boolalpha << success << std::endl;
    std::cout << error << std::endl;
    std::cout << executionTimeInMs << std::endl;
    std::cout << output << std::endl;
}

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

int main(int argc, char* argv[]) {
    int timeLimitInMs = std::stoi(argv[1]);

    try {
        auto startCompileTime = std::chrono::high_resolution_clock::now();
        int compileStatus = system(("g++ -o Main " + MAIN_CPP).c_str());
        auto endCompileTime = std::chrono::high_resolution_clock::now();
        int compileTimeInMs = std::chrono::duration_cast<std::chrono::milliseconds>(endCompileTime - startCompileTime).count();

        if (compileStatus != 0) {
            createResult(false, COMPILE_ERROR, compileTimeInMs, "");
            return 1;
        }

        int pid = fork();
        if (pid == 0) {
            // Redirect stdin from input file
            freopen(INPUT_FILE.c_str(), "r", stdin);
            // Execute the compiled program
            execl("./Main", "Main", (char *)NULL);
            // Exec functions return only if there is an error
            exit(127);
        } else {
            int status;
            auto startTime = std::chrono::high_resolution_clock::now();
            waitpid(pid, &status, 0);
            auto endTime = std::chrono::high_resolution_clock::now();
            long executionTimeInMs = std::chrono::duration_cast<std::chrono::milliseconds>(endTime - startTime).count();

            if (WIFEXITED(status) && WEXITSTATUS(status) != 0) {
                createResult(false, RUNTIME_ERROR, executionTimeInMs, "");
            } else {
                std::string output = exec("./Main < " + INPUT_FILE);
                createResult(true, "", executionTimeInMs, output);
            }
        }
    } catch (const std::exception &e) {
        createResult(false, "Server Error", 0, e.what());
    }

    return 0;
}

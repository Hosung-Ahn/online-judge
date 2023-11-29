#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>
#include <cstdio>
#include <array>
#include <memory>

const std::string CPP_FILE_NAME = "Main.cpp";
const std::string INPUT_PATH = "input.txt";
const std::string RESULT_PATH = "result.json";

struct Result {
    bool compile_error;
    std::string compile_info;
    bool runtime_error;
    std::string runtime_info;
    long execution_time;  // Milliseconds
    std::string output;
};

// Helper function to execute a command and capture the output
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

// Helper function to escape JSON characters
std::string escape_json(const std::string &s) {
    std::string escaped;
    for (auto c : s) {
        switch (c) {
            case '\\': escaped += "\\\\"; break;
            case '"': escaped += "\\\""; break;
            case '/': escaped += "\\/"; break;
            case '\b': escaped += "\\b"; break;
            case '\f': escaped += "\\f"; break;
            case '\n': escaped += "\\n"; break;
            case '\r': escaped += "\\r"; break;
            case '\t': escaped += "\\t"; break;
            default: escaped += c; break;
        }
    }
    return escaped;
}

void create_result(const Result& result) {
    std::ofstream result_file(RESULT_PATH);
    result_file << "{\n";
    result_file << "  \"compile_error\": " << (result.compile_error ? "true" : "false") << ",\n";
    result_file << "  \"compile_info\": \"" << escape_json(result.compile_info) << "\",\n";
    result_file << "  \"runtime_error\": " << (result.runtime_error ? "true" : "false") << ",\n";
    result_file << "  \"runtime_info\": \"" << escape_json(result.runtime_info) << "\",\n";
    result_file << "  \"execution_time\": " << result.execution_time << ",\n";
    result_file << "  \"output\": \"" << escape_json(result.output) << "\"\n";
    result_file << "}\n";
    result_file.close();
}

int main() {
    Result result = {false, "", false, "", -1, ""};

    // Compile the C++ program
    std::string compile_command = "g++ -o Main " + CPP_FILE_NAME;
    result.compile_info = exec(compile_command.c_str());
    if (!result.compile_info.empty()) {
        result.compile_error = true;
        create_result(result);
        return 0;
    }

    // Run the compiled C++ program
    std::string run_command = "./Main < " + INPUT_PATH;
    auto start = std::chrono::high_resolution_clock::now();
    result.output = exec(run_command.c_str());
    auto end = std::chrono::high_resolution_clock::now();

    // Check for runtime error (if the output is empty, we assume an error occurred)
    if (result.output.empty()) {
        result.runtime_error = true;
        result.runtime_info = "Runtime error or no output.";
    }

    // Calculate execution time in milliseconds
    result.execution_time = std::chrono::duration_cast<std::chrono::milliseconds>(end - start).count();

    create_result(result);
    return 0;
}

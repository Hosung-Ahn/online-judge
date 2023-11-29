#include <iostream>
#include <chrono>
#include <cstdlib>
#include <fstream>

using namespace std;

const string COMPILE_ERROR = "Compile Error";
const string RUNTIME_ERROR = "Runtime Error";
const string TIMEOUT_ERROR = "Timeout Error";

void createResult(bool success, const string &error, int executionTimeInMs, const string &output) {
    cout << boolalpha << success << "\n";
    cout << error << "\n";
    cout << executionTimeInMs << "\n";
    cout << output << "\n";
}

int main() {
    int compile_status = system("g++ -o Main Main.cpp");
    if (compile_status != 0) {
        createResult(false, COMPILE_ERROR, 0, "");
        return 1;
    }

    auto start = chrono::high_resolution_clock::now();
    int run_status = system("./Main < input.txt");
    auto end = chrono::high_resolution_clock::now();

    int executionTimeInMs = chrono::duration_cast<chrono::milliseconds>(end - start).count();


    ifstream file("output.txt");
//    if (!file.is_open()) {
//        std::cerr << "output.txt 파일을 열 수 없습니다." << std::endl;
//        return 1; // 파일 열기 실패
//    }
    if (run_status != 0) {
        createResult(false, RUNTIME_ERROR, 0, "");
        return 1;
    }

    string output((istreambuf_iterator<char>(file)), istreambuf_iterator<char>());
    createResult(true, "", executionTimeInMs, output);
}
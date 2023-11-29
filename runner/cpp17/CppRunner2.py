import subprocess
import json
import time
import argparse
import psutil

CPP_FILE_NAME = "./Main.cpp"
INPUT_PATH = "./input.txt"
RESULT_PATH = "./result/result.json"

COMPILE_ERROR = "Compile Error"
RUNTIME_ERROR = "Runtime Error"
TIMEOUT_ERROR = "Timeout Error"
MEMORY_ERROR = "Memory Error"
SERVER_ERROR = "Server Error"

parser = argparse.ArgumentParser(description="C++ 코드 실행 스크립트")
parser.add_argument("--timeout", type=int, default=2000, help="시간 제한 (밀리초 단위) / 기본값: 2000")
parser.add_argument("--memory", type=int, default=256 * 1024, help="메모리 제한 (킬로바이트 단위) / 기본값: 256 * 1024")
args = parser.parse_args()

result = {
    "error": None,
    "execution_time": None,  # (MS)
    "memory_usage": None,  # (KB)
    "output": None,
}

def create_result():
    print(result)
    with open(RESULT_PATH, 'w') as result_file:
        json.dump(result, result_file)

compile_command = f"g++ -std=c++17 -o ./Main {CPP_FILE_NAME}"
compilation = subprocess.run(compile_command, shell=True, capture_output=True)

if compilation.returncode != 0:
    result['error'] = COMPILE_ERROR
    result['output'] = compilation.stderr.decode('utf-8')  # 개발용
    create_result()
    exit(0)

run_command = f"./Main < {INPUT_PATH}"

start_time = time.perf_counter()

# C++ 프로그램 실행 및 메모리 사용량 측정
process = subprocess.Popen(run_command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
ps_process = psutil.Process(process.pid)

while True:
    current_time = time.perf_counter()
    elapsed_time = (current_time - start_time) * 1000  # 밀리초 단위로 변환

    if elapsed_time > args.timeout:  # 시간 초과 확인
        process.kill()
        result['error'] = TIMEOUT_ERROR
        create_result()
        exit(0)

    if process.poll() is not None:  # 프로세스가 종료되었는지 확인
        break

    try:
        current_memory = ps_process.memory_info().rss / 1024  # KB 단위로 변환
        memory_usage = max(memory_usage, current_memory)
    except psutil.NoSuchProcess:
        break

    time.sleep(0.1)  # 메모리 사용량 측정 간격

end_time = time.perf_counter()

if process.returncode != 0:
    result['error'] = RUNTIME_ERROR
    result['output'] = process.stderr.decode('utf-8')  # 개발용
    create_result()
    exit(0)

# 메모리 사용량 측정
memory_usage = ps_process.memory_info().rss / 1024  # KB 단위로 변환

execution_time = (end_time - start_time) * 1000
if memory_usage > args.memory:
    result['error'] = MEMORY_ERROR
    create_result()
    exit(0)

result['execution_time'] = execution_time
result['memory_usage'] = memory_usage
result['output'] = process.stdout.decode('utf-8')
create_result()

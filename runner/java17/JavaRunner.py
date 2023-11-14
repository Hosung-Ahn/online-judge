import resource
import subprocess
import json
import time
import argparse

JAVA_FILE_NAME = "./Main.java"
INPUT_PATH = "./input.txt"
RESULT_PATH = "./result/result.json"

COMPILE_ERROR = "Compile Error"
RUNTIME_ERROR = "Runtime Error"
TIMEOUT_ERROR = "Timeout Error"
MEMORY_ERROR = "Memory Error"
SERVER_ERROR = "Server Error"

parser = argparse.ArgumentParser(description="Java 코드 실행 스크립트")
parser.add_argument("--timeout", type=int, default=2000, help="시간 제한 (밀리초 단위) / 기본값: 2000")
parser.add_argument("--memory", type=int, default=256 * 1024, help="메모리 제한 (킬로바이트 단위) / 기본값: 256 * 1024")
args = parser.parse_args()

result = {
    "error" : None,
    "execution_time": None,  # (MS)
    "memory_usage": None,  # (KB)
    "output": None,
}

def create_result():
    print(result)
    with open(RESULT_PATH, 'w') as result_file:
        json.dump(result, result_file)

compile_command = f"javac {JAVA_FILE_NAME}"
compilation = subprocess.run(compile_command, shell=True, capture_output=True)

if compilation.returncode != 0:
    result['error'] = COMPILE_ERROR
    create_result()
    exit(0)

run_command = f"java Main < {INPUT_PATH}"

# 호출 프로세스가 종료된 모든 자식 프로세스의 누적 자원 사용 정보를 반환합니다
start_memory = resource.getrusage(resource.RUSAGE_CHILDREN)
start_time = time.perf_counter()
try:
    execution = subprocess.run(run_command, shell=True, capture_output=True, timeout=args.timeout)
except subprocess.TimeoutExpired:
    result['error'] = TIMEOUT_ERROR
    create_result()
    exit(0)
end_time = time.perf_counter()
end_memory = resource.getrusage(resource.RUSAGE_CHILDREN)

if execution.returncode != 0:
    result['error'] = RUNTIME_ERROR
    result['output'] = execution.stderr.decode('utf-8')  # 개발용
    create_result()
    exit(0)

execution_time = (end_time - start_time) * 1000
memory_usage = (end_memory.ru_maxrss - start_memory.ru_maxrss) # 우분투 -> kb

if memory_usage > args.memory:
    result['error'] = MEMORY_ERROR
    create_result()
    exit(0)

result['execution_time'] = execution_time
result['memory_usage'] = memory_usage
result['output'] = execution.stdout.decode('utf-8')
create_result()

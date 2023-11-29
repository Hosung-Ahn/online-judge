import resource
import subprocess
import json
import time

CPP_FILE_NAME = "./Main.cpp"
INPUT_PATH = "./input.txt"
RESULT_PATH = "./result.json"

result = {
    "compile_error": False,
    "runtime_error": False,
    "execution_time": None,  # (MS)
    "memory_usage": None,  # (KB)
    "output": None,
}


def create_result():
    print(result)
    with open(RESULT_PATH, 'w') as result_file:
        json.dump(result, result_file)


with open(CPP_FILE_NAME, 'r') as cpp_file:
    cpp_code = cpp_file.read()

compile_command = f"g++ -o ./Main {CPP_FILE_NAME}"
compilation = subprocess.run(compile_command, shell=True, capture_output=True)

if compilation.returncode != 0:
    result['compile_error'] = True
    create_result()
    exit(0)

run_command = f"./Main < {INPUT_PATH}"


# 호출 프로세스가 종료된 모든 자식 프로세스의 누적 자원 사용 정보를 반환합니다
start_memory = resource.getrusage(resource.RUSAGE_CHILDREN)
start_time = time.perf_counter()
execution = subprocess.run(run_command, shell=True, capture_output=True)
end_time = time.perf_counter()
if execution.returncode != 0:
    result['runtime_error'] = True
    create_result()
    exit(0)


end_memory = resource.getrusage(resource.RUSAGE_CHILDREN)

execution_time = (end_time - start_time) * 1000
memory_usage = (end_memory.ru_maxrss - start_memory.ru_maxrss) / 1024 # 운영체제에 따라 단위가 다릅니다. mac os는 B, linux는 KB

result['execution_time'] = execution_time
result['memory_usage'] = memory_usage
result['output'] = execution.stdout.decode('utf-8')
create_result()

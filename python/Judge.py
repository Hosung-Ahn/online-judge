import time
import tracemalloc
import sys
import io


# 양식
# 0,1 : 1은 정상, 0은 오류
# error : 오류 종류 ( 있다면 이 줄에서 끝 / 없으면 이 줄 생략하고 다음 줄 )
# time : 실행 시간 ( ms )
# memory : 메모리 사용량 ( byte )
# output : 출력
def run_script(script_path, input_file_path, time_limit=1, memory_limit=10000000):
    original_stdin = sys.stdin
    sys.stdin = open(input_file_path, 'r')

    output_capture = io.StringIO()
    sys.stdout = output_capture

    start_time = time.time()
    tracemalloc.start()
    try:
        with open(script_path, 'r') as file:
            exec(file.read(), {'__name__': '__main__'})
    except Exception as e:
        error_occurred = e.__class__.__name__
        result = f"0\n{error_occurred}"
        print(result)
        return
    finally:
        sys.stdin.close()
        sys.stdin = original_stdin
        sys.stdout = sys.__stdout__

    end_time = time.time()
    execution_time = end_time - start_time
    current, peak = tracemalloc.get_traced_memory()
    tracemalloc.stop()

    if execution_time > time_limit:
        result = f"0\ntime limit exceeded"
        print(result)
        return

    if peak > memory_limit:
        result = f"0\nmemory limit exceeded"
        print(result)
        return

    result = f"1\n{int(execution_time * 1000)}\n{peak}\n{output_capture.getvalue()}"
    print(result)
    return


# Usage
run_script('Main.py', 'input.txt')

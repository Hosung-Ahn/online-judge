import time
import sys
import io

MAIN_PATH = "Main.py"
INPUT_PATH = "input.txt"


def create_result(success, error, execution_time_in_ms, output):
    print(success)
    print(error)
    print(execution_time_in_ms)
    print(output)
    return


def run_script(time_limit_in_ms):
    original_stdin = sys.stdin
    original_stdout = sys.stdout
    sys.stdin = open(INPUT_PATH, 'r')
    output_capture = io.StringIO()
    sys.stdout = output_capture

    start_time = time.time()
    error = None

    try:
        with open(MAIN_PATH, 'r') as file:
            script_content = file.read()
        compile(script_content, MAIN_PATH, 'exec')
    except SyntaxError:
        error = "compile error"

    if error:
        sys.stdout = original_stdout
        sys.stdin = original_stdin
        print(1)
        create_result(False, error, 0, "")
        return

    try:
        # Executing the script
        exec(script_content, {'__name__': '__main__'})
    except Exception:
        error = "runtime error"

    sys.stdout = original_stdout
    sys.stdin = original_stdin

    end_time = time.time()
    execution_time_in_ms = int((end_time - start_time) * 1000)

    if execution_time_in_ms > time_limit_in_ms:
        error = "time limit exceeded"

    if error:
        create_result(False, error, execution_time_in_ms, "")
    else:
        output = output_capture.getvalue()
        create_result(True, "", execution_time_in_ms, output)


# Usage
run_script(10000)

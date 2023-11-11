import time
import sys
import io
import json
import uuid

MAIN_PATH = "Main.py"
INPUT_PATH = "input.txt"


def create_result(success, error_name, execution_time_in_ms, output):
    data = {
        "success": success,
        "error_name": error_name,
        "execution_time_in_ms": execution_time_in_ms,
        "output": output
    }

    filename = uuid.uuid4();
    with open(f"result/{filename}.json", "w") as file:
        json.dump(data, file)

    return filename

def run_script(time_limit_in_ms):
    original_stdin = sys.stdin
    original_stdout = sys.stdout
    sys.stdin = open(INPUT_PATH, 'r')
    output_capture = io.StringIO()
    sys.stdout = output_capture

    start_time = time.time()

    try:
        with open(MAIN_PATH, 'r') as file:
            exec(file.read(), {'__name__': '__main__'})
    except Exception as e:
        error_occurred = e.__class__.__name__
        sys.stdout = original_stdout
        sys.stdin = original_stdin
        print(create_result(False, error_occurred, 0, ""))
        return
    finally:
        sys.stdout = original_stdout
        sys.stdin = original_stdin

    end_time = time.time()
    execution_time_in_ms = int((end_time - start_time) * 1000)

    if execution_time_in_ms > time_limit_in_ms:
        print(create_result(False, "TimeLimitExceededError", execution_time_in_ms, ""))
        return

    output = output_capture.getvalue()
    print(create_result(True, "", execution_time_in_ms, output))


# Usage
run_script(10000)

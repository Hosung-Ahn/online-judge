import time
import tracemalloc
import sys


def run_script(script_path, input_file_path, time_limit=1, memory_limit=10000000):
    # Memory usage tracking start
    tracemalloc.start()

    # Redirect stdin to read from the input file
    original_stdin = sys.stdin
    sys.stdin = open(input_file_path, 'r')

    start_time = time.time()  # Start time
    try:
        # Execute the script
        with open(script_path, 'r') as file:
            exec(file.read(), {'__name__': '__main__'})
    except Exception as e:
        print(f"An error occurred: {e.__class__.__name__}")
    finally:
        sys.stdin.close()
        sys.stdin = original_stdin  # Reset stdin to its original value

    end_time = time.time()  # End time
    execution_time = end_time - start_time  # Calculate execution time

    # Check for time limit exceedance
    if execution_time > time_limit:
        print(f"Execution time exceeded the limit of {time_limit} seconds")

    # Memory usage
    current, peak = tracemalloc.get_traced_memory()
    tracemalloc.stop()  # Stop memory tracking

    # Check for memory limit exceedance
    if peak > memory_limit:
        print(f"Memory usage exceeded the limit of {memory_limit} bytes")

    # Print execution time and memory usage
    print(f"Execution time: {execution_time} seconds")
    print(f"Peak memory usage: {peak} bytes")

# Usage
run_script('Main.py', 'input.txt')

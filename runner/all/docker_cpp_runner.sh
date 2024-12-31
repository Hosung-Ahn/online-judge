docker run --rm --memory 512m \
 -v /Users/hosungan/Workspace/online-judge/runner/all/cpp_test/Main.cpp:/app/Main.cpp:ro \
 -v /Users/hosungan/Workspace/online-judge/runner/all/cpp_test/input.txt:/app/input.txt:ro \
 runner ./cpp_runner.sh 1
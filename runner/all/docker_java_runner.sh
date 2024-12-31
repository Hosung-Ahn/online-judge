docker run --rm --memory 512m \
 -v /Users/hosungan/Workspace/online-judge/runner/all/java_test/Main.cpp:/app/Main.java:ro \
 -v /Users/hosungan/Workspace/online-judge/runner/all/java_test/input.txt:/app/input.txt:ro \
 runner ./java_runner.sh 1
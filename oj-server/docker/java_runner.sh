#!/bin/bash

# 실행 시간 제한 (초 단위)
time_limit_s=$1

# 컴파일
javac -encoding UTF-8 Main.java 2> compile_error.log
if [ $? -ne 0 ]; then
    # 컴파일 에러 메시지를 JSON-safe 형식으로 변환
    errorDetails=$(jq -Rs . < compile_error.log)
    echo "{\"success\": false, \"error\": \"COMPILE_ERROR\", \"details\": $errorDetails}"
    exit 1
fi

# 실행 및 시간과 메모리 사용량 측정, 실행 결과를 output.txt에 저장
/usr/bin/time -f "{\"elapsedSeconds\":%e,\"usageKb\":%M}" -o time.json timeout $time_limit_s java -Xmx1024m Main < input.txt > output.txt 2> runtime_error.log

exitCode=$?
if [ $exitCode -eq 124 ]; then
    # 타임아웃 에러 메시지를 JSON-safe 형식으로 변환
    echo "{\"success\": false, \"error\": \"TIMEOUT_ERROR\", \"details\": \"Execution exceeded time limit of ${time_limit_s} seconds.\"}"
    exit 1
elif [ $exitCode -eq 137 ]; then
    # 메모리 초과 에러 메시지를 JSON-safe 형식으로 변환
    echo "{\"success\": false, \"error\": \"MEMORYOUT_ERROR\", \"details\": \"Process exceeded memory limit.\"}"
    exit 1
elif [ $exitCode -ne 0 ]; then
    # 런타임 에러 메시지를 JSON-safe 형식으로 변환
    errorDetails=$(jq -Rs . < runtime_error.log)
    echo "{\"success\": false, \"error\": \"RUNTIME_ERROR\", \"details\": $errorDetails}"
    exit 1
fi

# 성공적으로 실행되면 결과 JSON 파일 생성
elapsedSeconds=$(jq '.elapsedSeconds' time.json)
executionTimeMs=$(awk "BEGIN {print ($elapsedSeconds * 1000)}")
memoryUsage=$(jq '.usageKb' time.json)
# 출력 파일을 JSON-safe 형식으로 변환
jsonOutput=$(jq -Rs . < output.txt)

# 결과 반환
echo "{\"success\": true, \"timeUsageMs\": $executionTimeMs, \"memoryUsageKb\": $memoryUsage, \"output\": $jsonOutput}"
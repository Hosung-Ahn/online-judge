#!/bin/bash

# 실행 시간 제한 (초 단위)
time_limit_s=$1

# 실행 및 시간과 메모리 사용량 측정, 실행 결과를 output.txt에 저장
/usr/bin/time -f "{\"elapsedSeconds\":%e,\"usageKb\":%M}" -o time.json timeout $time_limit_s python3 Main.py < input.txt > output.txt

exitCode=$? 
if [ $exitCode -eq 124 ]; then
    # Timeout 에러 처리
    echo "{\"success\": false, \"error\": \"TIMEOUT_ERROR\"}"
    # echo "{\"success\": false, \"error\": \"TIMEOUT_ERROR\"}" > result/result.json
    # cat result/result.json
    exit 1
elif [ $exitCode -eq 137 ]; then
    # 메모리 초과 에러 처리
    echo "{\"success\": false, \"error\": \"MEMORYOUT_ERROR\"}" 
    # echo "{\"success\": false, \"error\": \"MEMORYOUT_ERROR\"}" > result/result.json
    # cat result/result.json
    exit 1
elif [ $exitCode -ne 0 ]; then
    # 기타 런타임 에러 처리
    echo "{\"success\": false, \"error\": \"RUNTIME_ERROR\"}" 
    # echo "{\"success\": false, \"error\": \"RUNTIME_ERROR\"}" > result/result.json
    # cat result/result.json
    exit 1
fi

# 성공적으로 실행되면 결과 JSON 파일 생성
elapsedSeconds=$(jq '.elapsedSeconds' time.json)
executionTimeMs=$(awk "BEGIN {print ($elapsedSeconds * 1000)}")
memoryUsage=$(jq '.usageKb' time.json)
# 출력 파일을 안전한 JSON 문자열로 변환
jsonOutput=$(jq -Rs . < output.txt)

# JSON 파일에 실행 결과 추가
echo "{\"success\": true, \"timeUsageMs\": $executionTimeMs, \"memoryUsageKb\": $memoryUsage, \"output\": $jsonOutput}" 
# echo "{\"success\": true, \"executionMs\": $executionTimeMs, \"usageKb\": $memoryUsage, \"output\": \"$programOutput\"}" > result/result.json


# cat result/result.json # 결과 JSON 파일 출력 (옵션)

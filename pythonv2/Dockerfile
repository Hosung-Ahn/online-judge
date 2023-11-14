# 베이스 이미지로 Ubuntu 사용
FROM ubuntu:latest

WORKDIR /app

# 필요한 소프트웨어 패키지 설치
RUN apt-get update && \
    apt-get install -y python3 python3-pip openjdk-11-jdk build-essential && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

COPY PythonRunnerV2.py ./
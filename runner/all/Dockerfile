# 우분투 기본 이미지 사용
FROM ubuntu:latest

WORKDIR /app

# 필요한 패키지 설치
RUN apt-get update && \
    apt-get install -y software-properties-common && \
    add-apt-repository ppa:deadsnakes/ppa && \
    apt-get update && \
    apt-get install -y python3.10 && \
    apt-get install -y build-essential && \
    apt-get install -y wget

# Java 설치
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk

# time 명령어 설치
RUN apt-get update && \
    apt-get install -y time

# jp 설치
RUN apt-get update && \
    apt-get install -y jq

# 각 언어별 실행기 
COPY cpp_runner.sh /app/cpp_runner.sh

RUN chmod +x /app/cpp_runner.sh
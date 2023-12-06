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

# Python 설치 확인
RUN python3.10 --version

# Java 설치 확인
RUN java --version

# g++ 설치 확인 (C++17 지원)
RUN g++ --version

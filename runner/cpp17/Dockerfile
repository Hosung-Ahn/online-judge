FROM ubuntu:latest

WORKDIR /app

RUN apt-get update && \
    apt-get install -y python3 python3-pip build-essential && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

COPY CppRunner.py ./
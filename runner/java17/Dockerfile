FROM ubuntu:latest

WORKDIR /app

RUN apt-get update && apt-get install -y \
    software-properties-common \
    python3 python3-pip

RUN add-apt-repository ppa:openjdk-r/ppa && \
    apt-get update && \
    apt-get install -y openjdk-17-jdk

COPY JavaRunner.py ./

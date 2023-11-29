package com.oj.service;

public enum Language {
    CPP, JAVA, PYTHON;

    public String getExtension() {
        switch (this) {
            case CPP:
                return ".cpp";
            case JAVA:
                return ".java";
            case PYTHON:
                return ".py";
            default:
                return "";
        }
    }

    public String getDockerImage() {
        switch (this) {
            case CPP:
                return "cpp-runner";
            case JAVA:
                return "java-runner";
            case PYTHON:
                return "python-runner";
            default:
                return "";
        }
    }
}

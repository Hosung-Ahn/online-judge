package com.oj.dto;

public enum Language {
    C, CPP, JAVA, PYTHON, JAVASCRIPT;

    public String getExtension() {
        switch (this) {
            case C:
                return ".c";
            case CPP:
                return ".cpp";
            case JAVA:
                return ".java";
            case PYTHON:
                return ".py";
            case JAVASCRIPT:
                return ".js";
            default:
                return "";
        }
    }
}

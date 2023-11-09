package com.oj.exception;

public class FileExecutionException extends RuntimeException{
    public FileExecutionException(String message) {
        super(message);
    }
    public FileExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}

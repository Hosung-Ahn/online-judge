package com.oj.exception;

public class FileCreateException extends RuntimeException{
    public FileCreateException(String message) {
        super(message);
    }
    public FileCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}

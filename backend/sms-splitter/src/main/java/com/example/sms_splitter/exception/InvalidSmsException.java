package com.example.sms_splitter.exception;

public class InvalidSmsException extends RuntimeException {
    
    public InvalidSmsException(String message) {
        super(message);
    }
    
    public InvalidSmsException(String message, Throwable cause) {
        super(message, cause);
    }
}
package com.github.ifuyo.error.database;

import com.github.ifuyo.error.CustomException;
import lombok.Getter;

@Getter
public class DatabaseException extends CustomException {
    public enum ErrorType {
        CONNECTION_ERROR,
        OTHER_ERROR
    }

    private final ErrorType errorType;

    public DatabaseException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public DatabaseException(String message, ErrorType errorType, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

}

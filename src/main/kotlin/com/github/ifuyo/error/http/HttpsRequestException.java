package com.github.ifuyo.error.http;

import com.github.ifuyo.error.CustomException;
import lombok.Getter;

@Getter
public class HttpsRequestException extends CustomException {
    public enum ErrorType {
        CONNECTION_ERROR, // 连接错误
        TIMEOUT_ERROR,    // 超时错误
        OTHER_ERROR       // 其他错误
    }

    private final int responseCode;
    private final ErrorType errorType;

    public HttpsRequestException(String message, int responseCode, ErrorType errorType) {
        super(message);
        this.responseCode = responseCode;
        this.errorType = errorType;
    }

    public HttpsRequestException(String message, int responseCode, ErrorType errorType, Throwable cause) {
        super(message, cause);
        this.responseCode = responseCode;
        this.errorType = errorType;
    }

}
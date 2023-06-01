package com.bestbuy.TransactionApp.exception;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class ApiRequestException extends RuntimeException implements Supplier<ApiRequestException> {
    private final HttpStatus httpStatus;
    public ApiRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApiRequestException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public ApiRequestException get() {
        return this;
    }
}

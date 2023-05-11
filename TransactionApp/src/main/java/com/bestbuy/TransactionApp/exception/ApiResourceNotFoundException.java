package com.bestbuy.TransactionApp.exception;

import org.springframework.http.HttpStatus;

public class ApiResourceNotFoundException extends ApiRequestException{

    public ApiResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public ApiResourceNotFoundException(String message, Throwable cause) {
        super(message, cause, HttpStatus.NOT_FOUND);
    }
}

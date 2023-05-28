package com.bestbuy.TransactionApp.exception;

import org.springframework.http.HttpStatus;

public class ApiBadRequestException extends ApiRequestException{

    public ApiBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public ApiBadRequestException(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST);
    }
}

package com.bestbuy.TransactionApp.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiRequestException{

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause, HttpStatus.UNAUTHORIZED);
    }
}

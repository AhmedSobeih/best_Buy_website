package com.bestbuy.TransactionApp.exception;

import org.springframework.stereotype.Component;

@Component
public class CartExceptionSupplier {
    public ApiRequestException notFound(Long cartId) {
        return new ApiResourceNotFoundException("Shopping cart with for user with Id: " + cartId + " does not exist");
    }

    public ApiRequestException alreadyExists(Long cartId) {
        return new ApiBadRequestException("Cart for user with Id: " + cartId + " already exists");
    }

}

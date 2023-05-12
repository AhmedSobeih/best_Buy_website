package com.bestbuy.TransactionApp.exception;

import org.springframework.stereotype.Component;

@Component
public class CartItemExceptionSupplier {
    public ApiRequestException notFound(Long cartItemId) {
        return new ApiResourceNotFoundException("Cart Item with Id: " + cartItemId + " does not exist");
    }


    public ApiRequestException invalidQuantity(Long cartItemId) {
        return new ApiBadRequestException("The quantity for cart item with id "+ cartItemId +" should be >= 0");
    }


}

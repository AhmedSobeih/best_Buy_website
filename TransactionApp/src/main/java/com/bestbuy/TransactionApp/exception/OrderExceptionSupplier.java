package com.bestbuy.TransactionApp.exception;

import org.springframework.stereotype.Component;

@Component
public class OrderExceptionSupplier {
    public ApiRequestException notFound(Long orderId) {
        return new ApiResourceNotFoundException("Order with Id: " + orderId + " does not exist");
    }

    public ApiRequestException emptyCart(Long userId) {
        return new ApiBadRequestException("Order Failed! Shopping cart for user " + userId + " is empty");
    }


}

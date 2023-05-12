package com.bestbuy.TransactionApp.exception;

import org.springframework.stereotype.Component;

@Component
public class StockExceptionSupplier {
    public ApiRequestException notFound(String productId) {
        return new ApiResourceNotFoundException("Stock with Id: " + productId + " does not exist");
    }

    public ApiRequestException alreadyExists(String productId) {
        return new ApiBadRequestException("Stock with Id: " + productId + " already exists");
    }

    public ApiRequestException cannotDecrement(String productId) {
        return new ApiBadRequestException("There is no enough stock for product with id: " + productId);
    }

    public ApiRequestException invalidDecrementAmount(Integer quantity) {
        String msg = quantity == null? "Attribute quantity missing":("Invalid decrement amount: "+quantity);
        return new ApiBadRequestException(msg);
    }

    public ApiRequestException invalidIncrementAmount(Integer quantity) {
        String msg = quantity == null? "Attribute quantity missing":("Invalid increment amount: "+quantity);
        return new ApiBadRequestException(msg);
    }

    public ApiRequestException invalidPrice() {
        return new ApiBadRequestException("Price should be > 0");
    }

    public ApiRequestException invalidQuantity() {
        return new ApiBadRequestException("Quantity should be >= 0");
    }

}

package com.productsApp.products.commands;

import com.productsApp.products.DTO.AddProductToStockRequest;
import com.productsApp.products.DTO.Request;

public abstract class Command {
    Request request;

    public Command setRequest(Request request){
        this.request=request;
        return this;
    }

    public Command AddProductRequest(Request request) {
        this.request=request;
        return this;
    }

    public Command CreateProduct(Request request) {
        this.request=request;
        return this;
    }

    abstract public void execute();


}

package com.userapp.commands;

import com.userapp.DTO.Request;
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

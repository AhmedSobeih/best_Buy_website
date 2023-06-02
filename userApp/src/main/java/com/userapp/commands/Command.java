package com.userapp.commands;

import com.userapp.DTO.Request;

public abstract class Command {
    Request request;

    abstract public void execute();


}

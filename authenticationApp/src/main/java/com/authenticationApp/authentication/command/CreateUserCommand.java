package com.authenticationApp.authentication.command;

import com.authenticationApp.authentication.controller.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateUserCommand implements Command{

    private final AuthenticationService authenticationService;
    private String username;

    @Autowired
    public CreateUserCommand(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void execute() {

    }

    @Override
    public void setMessageContent(String[] value) {

    }
}

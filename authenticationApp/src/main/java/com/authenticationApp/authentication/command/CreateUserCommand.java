package com.authenticationApp.authentication.command;

import com.authenticationApp.authentication.controller.AuthenticationService;
import com.authenticationApp.authentication.controller.RegisterRequest;
import com.authenticationApp.authentication.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateUserCommand implements Command{

    private final AuthenticationService authenticationService;
    private RegisterRequest registerRequest;
    private String username;
    private String password;
    private int user_id;
    private Role role;

    @Autowired
    public CreateUserCommand(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void setMessageContent(String[] value) {
        user_id = Integer.parseInt(value[0]);
        username = value[1];
        password = value[2];
        role = Role.valueOf(value[3]);
        registerRequest = new RegisterRequest(user_id,username,password,role);
    }

    @Override
    public String execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}

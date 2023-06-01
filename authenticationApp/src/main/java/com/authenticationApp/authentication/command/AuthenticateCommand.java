package com.authenticationApp.authentication.command;

import com.authenticationApp.authentication.controller.AuthenticationService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Data
@Component
public class AuthenticateCommand implements Command{


    public String token;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticateCommand(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void execute() {

        String tokenExists;
        try
        {
            String username = authenticationService.getUserNameFromToken();
            System.out.println(username);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setMessageContent(String[] messageContent) {
        token = messageContent[0];
    }

    public void setToken(String token) {
        this.token = token;
    }


}

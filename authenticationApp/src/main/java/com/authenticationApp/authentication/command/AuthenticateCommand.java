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
    public String execute() {
        String message_output = "authenticate;";

        try
        {
            message_output += authenticationService.replyToAuthenticateMessage(token);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return message_output;
    }

    @Override
    public void setMessageContent(String[] messageContent) {
        token = messageContent[0];
    }

    public void setToken(String token) {
        this.token = token;
    }


}

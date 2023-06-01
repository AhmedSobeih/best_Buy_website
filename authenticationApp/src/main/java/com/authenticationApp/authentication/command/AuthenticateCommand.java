package com.authenticationApp.authentication.command;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Data
@Component
public class AuthenticateCommand implements Command{

    @Autowired
    public RedisTemplate<String, String> redisTemplate;

    public String token;

    @Override
    public void execute() {

        String tokenExists;
        try
        {
            redisTemplate.opsForValue().set("myKey", "myValue");
            tokenExists = redisTemplate.opsForValue().get(token);
            System.out.println(tokenExists);
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

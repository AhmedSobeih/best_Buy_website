package com.bestbuy.TransactionApp.controller;


import com.bestbuy.TransactionApp.dto.OrderResponse;
import com.bestbuy.TransactionApp.queue.AuthenticationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rabbit")
public class RabbitMQController {

    @Autowired
    AuthenticationSender authenticationSender;

    @GetMapping("/test")
    public String test(@RequestParam String token){
        String message = authenticationSender.sendMessage("authenticate;"+token);
        return message;
    }
}

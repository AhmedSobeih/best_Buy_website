package com.bestbuy.TransactionApp.controller;


import com.bestbuy.TransactionApp.queue.AuthenticationSender;
import com.bestbuy.TransactionApp.queue.responses.AuthenticationResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rabbit")
public class RabbitMQController {

    @Autowired
    AuthenticationSender authenticationSender;

    @GetMapping("/test")
<<<<<<< HEAD
    public AuthenticationResponse test(@RequestParam String token){
        return authenticationSender.authenticateUser(token);
=======
    public String test(){
        authenticationSender.sendMessage("authenticate;token");
        return "messageSent";
>>>>>>> tran_backup
    }
}

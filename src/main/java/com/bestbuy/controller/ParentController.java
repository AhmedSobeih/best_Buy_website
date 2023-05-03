package com.bestbuy.controller;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/parent")
public class ParentController {

    private final RabbitTemplate rabbitTemplate;
    public ParentController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "AuthenticationSender")
    public void retrieveFromAuthentication(String test) {
        System.out.println(test);
        String message = "Welcome to our platform, " + test + "!";
        rabbitTemplate.convertAndSend("UserReceiver", message);
    }

    @RabbitListener(queues = "ProductsSender")
    public void retrieveFromProducts(String test) {
        
    }


    @RabbitListener(queues = "TransactionsSender")
    public void retrieveFromTransactions(String test) {
        String message = "Welcome to our platform, " + test + "!";
        rabbitTemplate.convertAndSend("UserReceiver", message);
    }

    @RabbitListener(queues = "MessageSender")
    public void retrieveFromMessage(String test) {
        String message = "Welcome to our platform, " + test + "!";
        rabbitTemplate.convertAndSend("UserReceiver", message);
    }


    @RabbitListener(queues = "UserSender")
    public void retrieveFromUser(String test) {
        String message = "Welcome to our platform, " + test + "!";
        rabbitTemplate.convertAndSend("UserReceiver", message);
    }

}

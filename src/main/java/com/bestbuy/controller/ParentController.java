package com.bestbuy.controller;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/parent")
public class ParentController {

//    @Autowired
//    private AuthenticationSender authenticationSender;
//    @Autowired
//    private AuthenticationReceiver authenticationReceiver;

    private final RabbitTemplate rabbitTemplate;
    public ParentController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "AuthenticationSender")
    public void test(String test) {
        System.out.println(test);
        String message = "Welcome to our platform, " + test + "!";
        rabbitTemplate.convertAndSend("UserReceiver", message);
    }
}

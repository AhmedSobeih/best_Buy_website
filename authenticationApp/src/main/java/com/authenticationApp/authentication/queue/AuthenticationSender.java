package com.authenticationApp.authentication.queue;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSender {
    private final RabbitTemplate rabbitTemplate;
    public AuthenticationSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("AuthenticationRec", message);
    }

}

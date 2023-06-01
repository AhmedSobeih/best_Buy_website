package com.bestbuy.TransactionApp.queue;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSender {
    private final RabbitTemplate rabbitTemplate;
    public AuthenticationSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("AuthenticationReceiver", message);
    }

    public String createMessage(String token)
    {
        return "authenticate;" + token;
    }
}

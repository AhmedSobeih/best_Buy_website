package com.bestbuy.TransactionApp.queue;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bestbuy.TransactionApp.queue.responses.AuthenticationResponse;

@Component
public class AuthenticationSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queues.auth.routingKey}")
    private String authRoutingKey;

    public AuthenticationSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public AuthenticationResponse authenticateUser(String token) {
        String message = "authenticate;" + token;
        String res = (String) rabbitTemplate.convertSendAndReceive(exchange, authRoutingKey, message);
        System.out.print("Authetication App response: " + res);
        return new AuthenticationResponse(res);
    }

    public String createMessage(String token)
    {
        return "authenticate;" + token;
    }
}

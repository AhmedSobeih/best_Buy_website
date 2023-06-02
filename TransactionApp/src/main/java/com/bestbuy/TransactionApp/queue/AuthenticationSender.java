package com.bestbuy.TransactionApp.queue;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
    public String sendMessage(String message) {
        String res = (String) rabbitTemplate.convertSendAndReceive(exchange, authRoutingKey, message);
        System.out.print("res: " + res);
        return res;
    }

    public String createMessage(String token)
    {
        return "authenticate;" + token;
    }
}

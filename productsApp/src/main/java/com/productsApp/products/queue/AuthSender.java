package com.productsApp.products.queue;

import com.productsApp.products.DTO.AuthRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AuthSender {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.queues.auth.routingKey}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public AuthSender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
    }


    public void sendAuthRequest(AuthRequest authRequest){
        rabbitTemplate.convertAndSend(exchange,routingKey,authRequest);
        System.out.printf("message sent %s %s",exchange,routingKey);
    }

}
package com.productsApp.products.queue;

import com.productsApp.products.DTO.AuthRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

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


    public String sendAuthRequest(AuthRequest authRequest){
        String res = (String)rabbitTemplate.convertSendAndReceive(exchange,routingKey,authRequest.getToken());
        System.out.print(res);
        return res;
    }

}

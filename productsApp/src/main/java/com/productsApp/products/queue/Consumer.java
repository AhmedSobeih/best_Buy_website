package com.productsApp.products.queue;

import com.productsApp.products.DTO.AuthRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @RabbitListener(queues = {"${rabbitmq.queues.auth.name}"})
    public void consumeMessage(AuthRequest authRequest){
        System.out.printf("Read auth request %s",authRequest.toString());
    }
}

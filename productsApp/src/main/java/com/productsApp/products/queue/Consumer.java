package com.productsApp.products.queue;

import com.productsApp.products.DTO.AuthRequest;
import com.productsApp.products.DTO.Request;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

//    @RabbitListener(queues = {"${rabbitmq.queues.auth.name}"})
//    public void consumeMessage(Request authRequest){
//        System.out.printf("Read auth request %s",authRequest.toString());
//    }
}

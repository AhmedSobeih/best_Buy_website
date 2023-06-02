package com.userapp.queue;

import com.userapp.entity.Role;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthSender {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.queues.auth.routingKey}")
    private String authRoutingKey;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public AuthSender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
    }


    public void sendAuthRequest(int id, String name, String password, Role role){
        rabbitTemplate.convertAndSend(exchange,authRoutingKey,"create_user;"+id+";"+name+";"+password+";"+role);
        System.out.printf("message sent %s %s",exchange,authRoutingKey);
    }
}

package com.userapp.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionSender {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.queues.transactions.routingKey}")
    private String transRoutingKey;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public TransactionSender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
    }

    public void sendTransactionRequest(int id){
        rabbitTemplate.convertSendAndReceive(exchange,transRoutingKey,"user_created;"+id);
        System.out.printf("message sent %s %s",exchange,transRoutingKey);
    }
}

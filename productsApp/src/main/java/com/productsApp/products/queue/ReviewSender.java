package com.productsApp.products.queue;

import com.productsApp.products.DTO.ReviewRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewSender {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.queues.review.routingKey}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public ReviewSender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
    }

    public List<String> getReviews(ReviewRequest reviewRequest){
        List<String> res=(List<String>)rabbitTemplate.convertSendAndReceive(exchange,routingKey,reviewRequest.getProductId());
        System.out.printf("message sent %s %s",exchange,routingKey);
        return res;
    }

}
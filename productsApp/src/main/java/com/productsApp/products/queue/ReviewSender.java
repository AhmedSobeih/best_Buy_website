package com.productsApp.products.queue;

import com.productsApp.products.DTO.ReviewRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public void sendReviewRequest(ReviewRequest ReviewRequest){
        rabbitTemplate.convertAndSend(exchange,routingKey,ReviewRequest);
        System.out.printf("message sent %s %s",exchange,routingKey);
    }

}

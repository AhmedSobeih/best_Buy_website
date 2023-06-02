package com.productReviewsApp.reviews.config;

import com.productReviewsApp.reviews.queue.ReviewsReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queues.review.name}")
    private String reviewQueueName;

    @Value("${rabbitmq.queues.review.routingKey}")
    private String reviewRoutingKey;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Bean
    public Queue reviewQueue(){
        return new Queue(reviewQueueName);
    }

    @Bean
    public Binding reviewBinding(){
        return BindingBuilder.bind(reviewQueue()).to(exchange()).with(reviewRoutingKey);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }


//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//                                             MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(reviewQueueName);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapter(ReviewsReceiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //rabbitTemplate.setMessageConverter(jsonConverter());
        return rabbitTemplate;
    }
}

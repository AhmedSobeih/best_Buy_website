package com.productsApp.products.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queues.auth.name}")
    private String authQueueName;

    @Value("${rabbitmq.queues.stock.name}")
    private String stockQueueName;

    @Value("${rabbitmq.queues.review.name}")
    private String reviewQueueName;
    @Value("${rabbitmq.queues.auth.routingKey}")
    private String authRoutingKey;

    @Value("${rabbitmq.queues.stock.routingKey}")
    private String stockRoutingKey;

    @Value("${rabbitmq.queues.review.routingKey}")
    private String reviewRoutingKey;
    @Bean
    public Queue authQueue(){
        return new Queue(authQueueName);
    }

    @Bean
    public Queue stockQueue(){
        return new Queue(stockQueueName);
    }

    @Bean
    public Queue reviewQueue(){
        return new Queue(reviewQueueName);
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding authBinding(){
        return BindingBuilder.bind(authQueue()).to(exchange()).with(authRoutingKey);
    }

    @Bean
    public Binding stockBinding(){
        return BindingBuilder.bind(stockQueue()).to(exchange()).with(stockRoutingKey);
    }

    @Bean
    public Binding reviewBinding(){
        return BindingBuilder.bind(reviewQueue()).to(exchange()).with(reviewRoutingKey);
    }

    @Bean
    public MessageConverter jsonConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonConverter());
        return rabbitTemplate;
    }
}
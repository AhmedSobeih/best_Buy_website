package com.productsApp.products.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queues.auth.name}")
    private String authQueueName;

    @Value("${rabbitmq.queues.auth.routingKey}")
    private String authRoutingKey;

    @Bean
    public Queue authQueue(){
        return new Queue(authQueueName);
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(authQueue()).to(exchange()).with(authRoutingKey);
    }
}

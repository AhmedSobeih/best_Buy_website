package com.userapp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queues.auth.routingKey}")
    private String authRoutingKey;

    @Value("${rabbitmq.queues.auth.name}")
    private String authQueueName;

    @Bean
    public Binding authBinding(){
        return BindingBuilder.bind(authQueue()).to(exchange()).with(authRoutingKey);
    }

    @Bean
    public Queue authQueue(){
        return new Queue(authQueueName);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }


    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
}

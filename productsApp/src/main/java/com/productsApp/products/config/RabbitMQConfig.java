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

    @Value("${rabbitmq.queues.auth.routingKey}")
    private String authRoutingKey;

    @Value("${rabbitmq.queues.stock.routingKey}")
    private String stockRoutingKey;

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

//    @Bean
//    public MessageConverter jsonConverter(){
//        return new Jackson2JsonMessageConverter();
//    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //rabbitTemplate.setMessageConverter(jsonConverter());
        return rabbitTemplate;
    }
}

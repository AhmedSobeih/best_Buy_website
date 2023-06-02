package com.bestbuy.TransactionApp.config;

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

    @Value("${rabbitmq.queues.transactions.name}")
    private String transactionsQueueName;

    @Value("${rabbitmq.queues.transactions.routingKey}")
    private String transactionsRoutingKey;



    @Bean
    public Queue transactionsQueue(){
        return new Queue(transactionsQueueName);
    }


    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding transactionsBinding(){
        return BindingBuilder.bind(transactionsQueue()).to(exchange()).with(transactionsRoutingKey);
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
}

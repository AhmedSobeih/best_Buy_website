package com.productsApp.products.queue;

import com.productsApp.products.DTO.AddProductToStockRequest;
import com.productsApp.products.DTO.AuthRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StockSender {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.queues.stock.routingKey}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public StockSender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
    }

    public void sendAddProductRequest(AddProductToStockRequest addProductToStockRequest){
        rabbitTemplate.convertAndSend(exchange,routingKey,addProductToStockRequest);
        System.out.printf("message sent %s %s",exchange,routingKey);
    }

    public void sendStockRequest(AuthRequest request) {
    }
}

package com.bestbuy.TransactionApp.controller;

import com.bestbuy.TransactionApp.dto.OrderResponse;
import com.bestbuy.TransactionApp.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Order Controller")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{user_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@PathVariable("user_id") Long userId){
        //TODO: try and catch exceptions
        OrderResponse orderResponse = orderService.placeOrder(userId);
        log.info("Order created with ID: {}", orderResponse);
        return orderResponse;
    }

    @GetMapping("/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders(@PathVariable("user_id") Long userId){
        return orderService.getAllOrders(userId);
    }

    @GetMapping("/{order_id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrder(@PathVariable("order_id") Long orderId){
        return orderService.getOrder(orderId);
    }

}

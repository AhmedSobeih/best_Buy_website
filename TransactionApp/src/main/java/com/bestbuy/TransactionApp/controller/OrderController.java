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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders(){
        log.info("Sent all orders");
        return orderService.getAllOrders();
    }

    @GetMapping("/{order_id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrder(@PathVariable("order_id") Long orderId){
        log.info("Sent order with id: {}", orderId);
        return orderService.getOrder(orderId);
    }

    @GetMapping("/user/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllUserOrders(@PathVariable("user_id") Long userId){
        log.info("Sent orders of user with id: {}", userId);
        return orderService.getAllUserOrders(userId);
    }

    @PostMapping("/{user_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@PathVariable("user_id") Long userId){
        log.info("Placed order for user with id: {}", userId);
        return orderService.placeOrder(userId);
    }


}

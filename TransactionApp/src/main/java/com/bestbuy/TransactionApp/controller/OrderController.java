package com.bestbuy.TransactionApp.controller;

import com.bestbuy.TransactionApp.dto.OrderResponse;
import com.bestbuy.TransactionApp.service.AuthenticatorService;
import com.bestbuy.TransactionApp.service.OrderService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Tag(name = "Order Controller")
public class OrderController {
    private final OrderService orderService;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final AuthenticatorService authenticatorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders(@RequestHeader("userToken") String userToken){
        authenticatorService.allowAdmin(userToken);
        
        logger.info("Sent all orders");
        return orderService.getAllOrders();
    }

    @GetMapping("/{order_id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrder(@RequestHeader("userToken") String userToken, @PathVariable("order_id") Long orderId){
        logger.info("Sent order with id: {}", orderId);
        OrderResponse orderResponse = orderService.getOrder(orderId);

        authenticatorService.allowOwnerUser(orderResponse.getUserId(), userToken);

        return orderResponse;
    }

    @GetMapping("/user/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllUserOrders(@RequestHeader("userToken") String userToken, @PathVariable("user_id") Long userId){
        authenticatorService.allowOwnerUser(userId, userToken);
        
        logger.info("Sent orders of user with id: {}", userId);
        return orderService.getAllUserOrders(userId);
    }

    @PostMapping("/{user_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@RequestHeader("userToken") String userToken, @PathVariable("user_id") Long userId){
        authenticatorService.allowOwnerUser(userId, userToken);

        logger.info("Placed order for user with id: {}", userId);
        return orderService.placeOrder(userId);
    }

    @DeleteMapping("/{order_id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse deleteOrder(@RequestHeader("userToken") String userToken, @PathVariable("order_id") Long orderId){
        authenticatorService.allowAdmin(userToken);

        logger.info("Deleted order with id: {}", orderId);
        return orderService.deleteOrder(orderId);
    }



}

package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.dto.OrderResponse;
import com.bestbuy.TransactionApp.model.Order;
import com.bestbuy.TransactionApp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderResponse placeOrder(Long userId) {

        return null;
    }

    public List<OrderResponse> getAllOrders(Long userId) {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> OrderResponse.mapOrder(order)).toList();
    }

    public OrderResponse getOrder(Long orderId) {
        Optional<Order> order = orderRepository.getOrderById(orderId);
        if(order.isPresent())
            return OrderResponse.mapOrder(order.get());
        else
            throw new NoSuchElementException("Order with id " + orderId + "doesn't exist");
    }

}

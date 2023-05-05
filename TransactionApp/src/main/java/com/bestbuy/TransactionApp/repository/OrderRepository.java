package com.bestbuy.TransactionApp.repository;

import com.bestbuy.TransactionApp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> getOrderById(Long Id);
    List<Order> getByUserId(Long userId);
}

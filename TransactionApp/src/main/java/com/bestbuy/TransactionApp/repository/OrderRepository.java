package com.bestbuy.TransactionApp.repository;

import com.bestbuy.TransactionApp.model.Order;
import com.bestbuy.TransactionApp.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> getOrderById(Long Id);
}

package com.bestbuy.TransactionApp.repository;

import com.bestbuy.TransactionApp.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}

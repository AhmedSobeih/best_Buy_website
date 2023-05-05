package com.bestbuy.TransactionApp.repository;

import com.bestbuy.TransactionApp.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}

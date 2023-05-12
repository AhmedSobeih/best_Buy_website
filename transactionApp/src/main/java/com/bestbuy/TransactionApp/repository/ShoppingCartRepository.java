package com.bestbuy.TransactionApp.repository;

import com.bestbuy.TransactionApp.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> getShoppingCartByUserId(Long userId);

}

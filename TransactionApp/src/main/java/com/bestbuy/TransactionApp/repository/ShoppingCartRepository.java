package com.bestbuy.TransactionApp.repository;

import com.bestbuy.TransactionApp.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

}

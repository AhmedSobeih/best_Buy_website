package com.bestbuy.TransactionApp.repository;

import com.bestbuy.TransactionApp.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,String> {
    Optional<Stock> getStockByProductId(String productId);
}

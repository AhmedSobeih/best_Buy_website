package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.model.Stock;
import com.bestbuy.TransactionApp.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public boolean isInStock(String productId) {
        return stockRepository.getStockByProductId(productId).isPresent();
    }
}

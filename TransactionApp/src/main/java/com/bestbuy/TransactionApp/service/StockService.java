package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.dto.StockResponse;
import com.bestbuy.TransactionApp.model.Stock;
import com.bestbuy.TransactionApp.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;


    public boolean isInStock(String productId) {
        return stockRepository.getStockByProductId(productId).isPresent();
    }

    public Stock createStock(String productId, Double price, Integer quantity) {
        Stock stock = new Stock(productId, price, quantity);
        return stockRepository.save(stock);
    }
    public List<StockResponse> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream().map(stock->mapToProductResponse(stock)).toList();
    }

    public StockResponse getStockById(String productId) {
         return mapToProductResponse(stockRepository.getStockByProductId(productId).get());
    }

    private StockResponse mapToProductResponse(Stock stock) {
        return StockResponse.builder()
                .productId(stock.getProductId())
                .price(stock.getPrice()).quantity(stock.getQuantity())
                .build();
    }
}

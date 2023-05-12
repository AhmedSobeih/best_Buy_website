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

    public StockResponse decrementStock(String productId, Integer quantity) {
        Optional<Stock> stockOptional = canDecrementStock(productId, quantity);
        if (!stockOptional.isPresent()) {
            return null;
        }
        Stock stock = stockOptional.get();
        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);
        return mapToStockResponse(stock);
    }

    public StockResponse incrementStock(String productId, Integer quantity) {
        Optional<Stock> optionalStock = getStockByProductId(productId);
        System.out.println(optionalStock.get());
        if (optionalStock.isEmpty()) {
            return null;
        }
        Stock stock = optionalStock.get();
        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepository.save(stock);
        return mapToStockResponse(stock);
    }

    public Optional<Stock> canDecrementStock(String productId, Integer quantity) {
        Optional<Stock> stock = getStockByProductId(productId);
        if (stock.isPresent()) {
            if (stock.get().getQuantity() >= quantity) {
                return stock;
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    public Optional<Stock> getStockByProductId(String productId) {
        Optional<Stock> stock = stockRepository.getStockByProductId(productId);
        return stock;
    }

    public Boolean inStock(String productId) {
        Optional<Stock> stock = getStockByProductId(productId);
        return stock.isPresent() && stock.get().getQuantity() > 0;
    }

    public StockResponse createStock(String productId, Double price, Integer quantity) {
        Stock stock = new Stock(productId, price, quantity);
        stockRepository.save(stock);
        return mapToStockResponse(stock);
    }

    public List<StockResponse> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream().map(this::mapToStockResponse).toList();
    }

    public StockResponse getStockById(String productId) {
        return mapToStockResponse(stockRepository.getStockByProductId(productId).get());
    }

    private StockResponse mapToStockResponse(Stock stock) {
        return StockResponse.builder()
                .productId(stock.getProductId())
                .price(stock.getPrice()).quantity(stock.getQuantity())
                .build();
    }
}

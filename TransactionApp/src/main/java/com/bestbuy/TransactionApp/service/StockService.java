package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.dto.StockResponse;
import com.bestbuy.TransactionApp.model.Stock;
import com.bestbuy.TransactionApp.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public Boolean decrementStock(String productId,Integer quantity){
        Optional<Stock> stockOptional = canDecrementStock(productId, quantity);
        if(!stockOptional.isPresent()){
            return false;
        }
        Stock stock = stockOptional.get();

        stock.setQuantity(stock.getQuantity()-quantity);
        if(stock.getQuantity()==0){
            stockRepository.deleteById(stock.getProductId());
        }
        return true;
    }

    public Optional<Stock> canDecrementStock(String productId, Integer quantity) {
        Optional<Stock> stock = stockRepository.getStockByProductId(productId);
        if(stock.isPresent()) {
            if (stock.get().getQuantity() >= quantity) {
                return stock;
            }
            return Optional.empty();
        }
        return Optional.empty();
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

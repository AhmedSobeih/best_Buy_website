package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.dto.StockRequest;
import com.bestbuy.TransactionApp.dto.StockResponse;
import com.bestbuy.TransactionApp.exception.StockExceptionSupplier;
import com.bestbuy.TransactionApp.model.Stock;
import com.bestbuy.TransactionApp.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final StockExceptionSupplier stockExceptionSupplier;

    public StockResponse decrementStock(String productId, Integer quantity) {
        Stock stock = canDecrementStockOrThrow(productId, quantity);
        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);
        return mapToStockResponse(stock);
    }

    public StockResponse incrementStock(String productId, Integer quantity) {
        Stock stock = getStockByProductId(productId);
        if(quantity == null || quantity <= 0)
            throw stockExceptionSupplier.invalidIncrementAmount(quantity);
        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepository.save(stock);
        return mapToStockResponse(stock);
    }

    // returns the stock if it can be decremented, otherwise throws
    public Stock canDecrementStockOrThrow(String productId, Integer quantity) {
        Stock stock = getStockByProductId(productId);
        if(quantity == null || quantity <= 0)
            throw stockExceptionSupplier.invalidDecrementAmount(quantity);
        if (stock.getQuantity() >= quantity)
                return stock;
        else
            throw stockExceptionSupplier.cannotDecrement(productId, stock.getQuantity(), quantity);
    }

    public Stock getStockByProductId(String productId) {
        return stockRepository.getStockByProductId(productId).orElseThrow(stockExceptionSupplier.notFound(productId));
    }

    public Boolean inStock(String productId) {
        Optional<Stock> stock = stockRepository.getStockByProductId(productId);
        return stock.isPresent() && stock.get().getQuantity() > 0;
    }

    public StockResponse createStock(String productId, Double price, Integer quantity) {
        if(stockRepository.existsById(productId))   // we can choose to increment amount instead
            throw stockExceptionSupplier.alreadyExists(productId);
        if(quantity < 0) throw stockExceptionSupplier.invalidQuantity();
        if(price <= 0) throw stockExceptionSupplier.invalidPrice();
        Stock stock = new Stock(productId, price, quantity);
        stockRepository.save(stock);
        return mapToStockResponse(stock);
    }

    public List<StockResponse> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream().map(this::mapToStockResponse).toList();
    }

    public StockResponse getStockResponseById(String productId) {
        return mapToStockResponse(stockRepository.getStockByProductId(productId).orElseThrow(stockExceptionSupplier.notFound(productId)));
    }

    @Transactional
    public StockResponse updateStock(StockRequest stockRequest) {
        Stock stock = getStockByProductId(stockRequest.getProductId());
        if(stockRequest.getQuantity() != null) {
            if(stockRequest.getQuantity() >= 0)
                stock.setQuantity(stockRequest.getQuantity());
            else
                throw stockExceptionSupplier.invalidQuantity();
        }
        if(stockRequest.getPrice() != null) {
            if(stockRequest.getPrice() > 0)
                stock.setPrice(stockRequest.getPrice());
            else
                throw stockExceptionSupplier.invalidPrice();
        }
        return mapToStockResponse(stock);
    }

    private StockResponse mapToStockResponse(Stock stock) {
        return StockResponse.builder()
                .productId(stock.getProductId())
                .price(stock.getPrice()).quantity(stock.getQuantity())
                .build();
    }

}

package com.bestbuy.TransactionApp.controller;

import com.bestbuy.TransactionApp.dto.StockRequest;
import com.bestbuy.TransactionApp.dto.StockResponse;
import com.bestbuy.TransactionApp.model.Stock;
import com.bestbuy.TransactionApp.repository.StockRepository;
import com.bestbuy.TransactionApp.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@Slf4j
public class StockController {
    private final StockService stockService;
    @GetMapping("/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("product_id") String productId){
        log.info("Received stock check request for product_id: {}", productId);
        return stockService.canDecrementStock(productId,1).isPresent();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StockResponse> getAllStocks(){
        return stockService.getAllStocks();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String createStock(@RequestBody StockRequest stockRequest) {
        Stock stock = stockService.createStock(stockRequest.getProductId(), stockRequest.getPrice(), stockRequest.getQuantity());
        return "Stock Created Successfully";
    }
}

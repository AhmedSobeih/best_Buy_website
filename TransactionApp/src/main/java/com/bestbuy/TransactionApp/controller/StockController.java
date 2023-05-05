package com.bestbuy.TransactionApp.controller;

import com.bestbuy.TransactionApp.dto.StockRequest;
import com.bestbuy.TransactionApp.dto.StockResponse;
import com.bestbuy.TransactionApp.service.StockService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Stock Controller")
public class StockController {
    private final StockService stockService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StockResponse> getAllStocks(){
        log.info("Sent all stocks");
        return stockService.getAllStocks();
    }

    @GetMapping("/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    public StockResponse isInStock(@PathVariable("product_id") String productId){
        log.info("Sent stock of product with id: {}", productId);
        return stockService.getStockById(productId);
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponse createStock(@RequestBody StockRequest stockRequest) {
        log.info("Created stock: {}", stockRequest);
        return stockService.createStock(stockRequest.getProductId(), stockRequest.getPrice(), stockRequest.getQuantity());
    }
}

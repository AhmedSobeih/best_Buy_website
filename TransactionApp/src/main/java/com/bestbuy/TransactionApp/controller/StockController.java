package com.bestbuy.TransactionApp.controller;

import com.bestbuy.TransactionApp.dto.StockRequest;
import com.bestbuy.TransactionApp.dto.StockResponse;
import com.bestbuy.TransactionApp.service.AuthenticatorService;
import com.bestbuy.TransactionApp.service.StockService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
@Tag(name = "Stock Controller")
public class StockController {
    private final StockService stockService;
    private final Logger logger = LoggerFactory.getLogger(StockController.class);
    private final AuthenticatorService authenticatorService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StockResponse> getAllStocks(@RequestHeader("userToken") String userToken){
        authenticatorService.allowAdmin(userToken);

        logger.info("Returned all stocks");
        return stockService.getAllStocks();
    }

    @GetMapping("/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    public StockResponse getStockById(@PathVariable("product_id") String productId){
        logger.info("Sent stock of product with id: {}", productId);
        return stockService.getStockResponseById(productId);
    }

    @PutMapping ("/increment-stock")
    @ResponseStatus(HttpStatus.OK)
    public StockResponse incrementStockQuantity (@RequestHeader("userToken") String userToken, @RequestBody  StockRequest stockRequest){
        authenticatorService.allowAdmin(userToken);

        logger.info("Incremented Stock quantity --> product id: {}, added quantity: {}", stockRequest.getProductId(), stockRequest.getQuantity());
        return stockService.incrementStock(stockRequest.getProductId(),stockRequest.getQuantity());
    }

    @PutMapping ("/decrement-stock")
    @ResponseStatus(HttpStatus.OK)
    public StockResponse decrementStockQuantity (@RequestHeader("userToken") String userToken, @RequestBody  StockRequest stockRequest){
        authenticatorService.allowAdmin(userToken);

        logger.info("Decremented Stock quantity --> product id: {}, subtracted quantity: {}", stockRequest.getProductId(), stockRequest.getQuantity());
        return stockService.decrementStock(stockRequest.getProductId(),stockRequest.getQuantity());
    }

    @PutMapping ("/")
    @ResponseStatus(HttpStatus.OK)
    public StockResponse updateStock (@RequestHeader("userToken") String userToken, @RequestBody  StockRequest stockRequest){
        authenticatorService.allowAdmin(userToken);

        logger.info("Updated Stock: ", stockRequest);
        return stockService.updateStock(stockRequest);
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponse createStock(@RequestHeader("userToken") String userToken, @RequestBody StockRequest stockRequest) {
        authenticatorService.allowAdmin(userToken);

        logger.info("Created stock: {}", stockRequest);
        return stockService.createStock(stockRequest.getProductId(), stockRequest.getPrice(), stockRequest.getQuantity());
    }
}

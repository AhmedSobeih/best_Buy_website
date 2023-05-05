package com.bestbuy.TransactionApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {
    private String productId;
    private Double price;
    private Integer quantity;
}

package com.productsApp.products.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToStockRequest {
    private String token;
    private String productId;
    private int quantity;
    private double price;
}

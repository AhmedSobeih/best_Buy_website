package com.productsApp.products.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToStockRequest implements Request{
    private String token;
    private String productId;
    private int quantity;
    private double price;

    @Override
    public String requestType() {
        return "add_product_to_stock";
    }
}

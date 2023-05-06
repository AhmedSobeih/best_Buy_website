package com.bestbuy.TransactionApp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponse {
    private Long id;
    private String productId;
    private Integer quantity;
}

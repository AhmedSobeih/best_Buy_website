package com.bestbuy.TransactionApp.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartItemRequest {
    private Long userId;
    private Long id;
    private String productId;
    private Integer quantity;
}

package com.bestbuy.TransactionApp.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class ShoppingCartResponse {

    private Long userId;
    private LocalDateTime createdAt;
    private List<CartItemResponse> cartItemList;
}

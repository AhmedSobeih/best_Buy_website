package com.bestbuy.TransactionApp.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ShoppingCartResponse {

    private Long userId;
    private LocalDateTime createdAt;
    //TODO: List<CartItem> cartItemList;
}

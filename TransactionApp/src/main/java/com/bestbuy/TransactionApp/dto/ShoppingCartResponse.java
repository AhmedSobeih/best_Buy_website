package com.bestbuy.TransactionApp.dto;

import com.bestbuy.TransactionApp.model.CartItem;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class ShoppingCartResponse {

    private Long userId;
    private LocalDateTime createdAt;
    //TODO: List<CartItem> cartItemList;
}

package com.bestbuy.TransactionApp.dto;

import com.bestbuy.TransactionApp.model.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class OrderResponse {

    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    List<OrderItem> orderItemList;   


}

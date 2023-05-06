package com.bestbuy.TransactionApp.dto;

import com.bestbuy.TransactionApp.model.Order;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class OrderResponse {

    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
//    List<OrderItem> orderItemList;    // TODO: Convert to orderItem response

    public static OrderResponse mapOrder(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .createdAt(order.getCreatedAt())
                .build();
    }
}

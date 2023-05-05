package com.bestbuy.TransactionApp.dto;

import com.bestbuy.TransactionApp.model.Order;
import com.bestbuy.TransactionApp.model.OrderItem;
import com.bestbuy.TransactionApp.model.Stock;
import com.bestbuy.TransactionApp.repository.OrderRepository;
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
//    List<OrderItem> orderItemList;    // TODO: Convert to orderItem response

    public static OrderResponse mapOrder(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .createdAt(order.getCreatedAt())
                .build();
    }
}

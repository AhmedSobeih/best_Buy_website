package com.bestbuy.TransactionApp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {
    private String productId;
    private Double price;
    private Integer quantity;
}

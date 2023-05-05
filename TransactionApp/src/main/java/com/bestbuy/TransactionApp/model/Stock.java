package com.bestbuy.TransactionApp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;

@Table(name = "stock")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @Column(name = "product_id",nullable = false)
    private String productId;
    private Double price;
    @Column(columnDefinition = "int default 0")
    @Check(constraints = "quantity>=0")
    private Integer quantity;
}

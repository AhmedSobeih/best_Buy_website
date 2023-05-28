package com.bestbuy.TransactionApp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

@Table(name = "stock")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Stock {
    @Id
    @Column(name = "product_id",nullable = false)
    private String productId;
    @Check(constraints = "price>0")
    private Double price;
    @Column(columnDefinition = "int default 1")
    @Check(constraints = "quantity>=0")
    private Integer quantity;
}

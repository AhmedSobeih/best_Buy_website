package com.bestbuy.TransactionApp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

@Table(name = "cart_item")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productId;
    @Column(columnDefinition = "int default 1")
    @Check(constraints = "quantity>0")
    private Integer quantity;
}

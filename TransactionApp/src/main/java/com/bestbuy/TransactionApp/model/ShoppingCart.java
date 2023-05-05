package com.bestbuy.TransactionApp.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "shopping_cart")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ShoppingCart {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;
    @OneToMany(cascade = CascadeType.ALL)
    List<CartItem> cartItemList;

}

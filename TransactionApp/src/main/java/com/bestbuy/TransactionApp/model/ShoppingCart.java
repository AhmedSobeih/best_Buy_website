package com.bestbuy.TransactionApp.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<CartItem> cartItemList;

    public ShoppingCart(Long userId){
        this.userId = userId;
        this.cartItemList = new ArrayList<>();
    }

}

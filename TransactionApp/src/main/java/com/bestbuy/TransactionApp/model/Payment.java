package com.bestbuy.TransactionApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "payment")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double amount;
    @Column(name ="payment_date" ,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDate paymentDaSte;
    @Column(name = "payment_method")        
    private PaymentMethod paymentMethod;
    @Column(name = "status")
    private PaymentStatus paymentStatus;
    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;
}

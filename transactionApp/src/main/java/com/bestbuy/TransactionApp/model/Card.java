package com.bestbuy.TransactionApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "card", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "card_last4"}, name = "unique_card_user_id_card_last4"))
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "card_last4", nullable = false)
    private String cardLast4;

    @Column(name = "card_type", nullable = false)
    private String cardType;

    @Column(name = "card_exp_date", nullable = false)
    private LocalDate cardExpDate;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}

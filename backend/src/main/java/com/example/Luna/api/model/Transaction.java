package com.example.Luna.api.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "book_id", nullable = false)
    private Integer book_id;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
package com.example.Luna.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Entity
@Table(name = "categories")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime date;

    @ManyToMany(mappedBy = "books")
    private Set<Book> books;

}

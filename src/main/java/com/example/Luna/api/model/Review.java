package com.example.Luna.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "vote", nullable = false)
    private Boolean vote;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "date", nullable = false)
    private Timestamp sqlTimestamp = Timestamp.from(Instant.now());

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

}


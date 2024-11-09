package com.example.Luna.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "vote", nullable = false)
    private Boolean vote;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "date", nullable = false)
    private java.sql.Timestamp sqlTimestamp = Timestamp.from(Instant.now());

    @ManyToMany
    @JoinTable(name = "user_reviews",
            joinColumns = {@JoinColumn( name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    @JsonIgnoreProperties("reviews")
    private Set<Tag> user_reviews = new HashSet<>();

}

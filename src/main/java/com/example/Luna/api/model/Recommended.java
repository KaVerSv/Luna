package com.example.Luna.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recommended")
public class Recommended {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "recommended_books",
            joinColumns = {@JoinColumn( name = "recommended_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    @JsonIgnoreProperties("recommended")
    private Set<Book> recommended_tables = new HashSet<>();

}

package com.example.Luna.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class Book {

    public Book(Integer id, String title, String author, String publish_date, String description, BigDecimal price, String image, Integer likes, Integer dislikes) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publish_date = publish_date;
        this.description = description;
        this.price = price;
        this.image = image;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "publish_date")
    private String publish_date;

    @Column(name = "description")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "image")
    private String image;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "dislikes")
    private Integer dislikes;

    @Column(name = "user_score")
    private Integer user_score;

    @Column(name = "num_of_pages")
    private Integer num_of_pages;

    @ManyToMany(mappedBy = "books")
    @JsonIgnoreProperties("books")
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "book_tags",
            joinColumns = {@JoinColumn( name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    @JsonIgnoreProperties("books")
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "book_discounts",
        joinColumns = {@JoinColumn( name = "book_id")},
        inverseJoinColumns = {@JoinColumn(name = "discount_id")})
    @JsonIgnoreProperties("books")
    private Set<Discount> discounts = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "book_languages",
            joinColumns = {@JoinColumn( name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "language_id")})
    @JsonIgnoreProperties("books")
    private Set<Language> languages = new HashSet<>();
}

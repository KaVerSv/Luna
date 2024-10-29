package com.example.Luna.api.dto;

import com.example.Luna.api.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String publish_date;
    private String description;
    private BigDecimal price;
    private String image;
    private Integer likes;
    private Integer dislikes;
    private Double user_score;
    private Integer num_of_pages;
    private Set<TagDto> tags;
    private Set<LanguageDto> languages;

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publish_date = book.getPublish_date();
        this.description = book.getDescription();
        this.price = book.getPrice();
        this.image = book.getImage();
        this.likes = book.getLikes();
        this.dislikes = book.getDislikes();
        this.user_score = book.getUser_score();
        this.num_of_pages = book.getNum_of_pages();
        this.tags = book.getTags().stream().map(TagDto::new).collect(Collectors.toSet());
        this.languages = book.getLanguages().stream().map(LanguageDto::new).collect(Collectors.toSet());
    }
}

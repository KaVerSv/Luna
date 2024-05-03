package com.example.Luna.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Integer id;
    private String title;
    private String author;
    private String publish_date;
    private String description;
    private double price;
    private String image;
    private Integer likes;
    private Integer dislikes;
}

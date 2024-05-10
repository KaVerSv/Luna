package com.example.Luna.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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
    private BigDecimal price;
    private String image;
    private Integer likes;
    private Integer dislikes;
}

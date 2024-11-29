package com.example.Luna.api.mapper;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.model.Book;

public class BookMapper {
    public static Book mapToBook(BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getAuthor(),
                bookDto.getPublish_date(),
                bookDto.getDescription(),
                bookDto.getPrice(),
                bookDto.getImage(),
                bookDto.getLikes(),
                bookDto.getDislikes()
        );
    }
}

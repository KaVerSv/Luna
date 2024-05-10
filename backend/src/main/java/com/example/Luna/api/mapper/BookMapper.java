package com.example.Luna.api.mapper;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.model.Book;

public class BookMapper {

    public static BookDto mapToBookDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublish_date(),
                book.getDescription(),
                book.getPrice(),
                book.getImage(),
                book.getLikes(),
                book.getDislikes()
        );
    }

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

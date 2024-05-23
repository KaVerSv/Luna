package com.example.Luna.service;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.dto.BookWithDiscountDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    BookDto createBook(BookDto bookDto);

    BookDto getBookById(int bookId);

    List<BookDto> getAllBooks();

    List<BookDto> getFeaturedBooks(String name);

    List<BookWithDiscountDto> getFeaturedBooksAndDiscounts(String name);
}

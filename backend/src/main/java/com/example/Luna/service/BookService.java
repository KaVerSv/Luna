package com.example.Luna.service;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.dto.BookWithDiscountDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface BookService {
    BookDto createBook(BookDto bookDto);

    BookWithDiscountDto getBookById(int bookId);

    List<BookDto> getAllBooks();

    List<BookDto> getFeaturedBooks(String name);

    List<BookWithDiscountDto> getFeaturedBooksAndDiscounts(String name);

    List<BookWithDiscountDto> searchByTitle(String title);

    List<BookWithDiscountDto> searchByTitle(String keyword, Integer pageNum, Integer pageSize, BigDecimal bottomPriceRange,
                                            BigDecimal topPriceRange, String sortOption, Boolean specialOffers, List<String> languages, List<String> tags);
}

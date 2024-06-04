package com.example.Luna.service;

import com.example.Luna.api.dto.DiscountDto;
import com.example.Luna.api.model.Book;

import java.util.List;

public interface DiscountService {
    DiscountDto getDiscountByBookId(int id);
    DiscountDto createDiscount(DiscountDto discountDto);
    List<DiscountDto> createDiscounts(List<DiscountDto> discountDtos);
    DiscountDto createDiscountForBook(int bookId, DiscountDto discountDto);
    List<Book> getBooksOnActiveDiscount(List<Book> books);
}

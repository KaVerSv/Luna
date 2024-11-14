package com.example.Luna.service;

import com.example.Luna.api.dto.DiscountDto;
import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.Discount;
import com.example.Luna.api.model.Order;
import com.example.Luna.api.model.User;

import java.util.List;

public interface DiscountService {
    DiscountDto getDiscountDtoByBookId(long id);
    DiscountDto createDiscount(DiscountDto discountDto);
    List<DiscountDto> createDiscounts(List<DiscountDto> discountDtos);
    DiscountDto createDiscountForBook(long bookId, DiscountDto discountDto);
    List<Book> getBooksOnActiveDiscount(List<Book> books);
    Order createOrder(User user, List<Book> books);
    Discount getDiscountByBook(Book book);
}

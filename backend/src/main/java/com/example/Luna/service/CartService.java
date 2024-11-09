package com.example.Luna.service;

import com.example.Luna.api.dto.BookDto;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    List<BookDto> getUserCart();
    void addToCart(int id);
    void removeFromCart(int id);
    BigDecimal getTotalPrice();
    void addToLibrary();
}

package com.example.Luna.service;

import com.example.Luna.api.dto.BookWithDiscountDto;
import com.example.Luna.api.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    List<BookWithDiscountDto> getUserCartDto();
    void addToCart(int id);
    void removeFromCart(int id);
    BigDecimal getTotalPrice();
    void addToLibraryAndClear(User user);
}

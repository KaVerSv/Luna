package com.example.Luna.service;

import com.example.Luna.api.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    List<BookDto> getUserCart(HttpServletRequest request);
    void addToCart(HttpServletRequest request,int id);
    void removeFromCart(HttpServletRequest request,int id);
    BigDecimal getTotalPrice(HttpServletRequest request);
    void addToLibrary(HttpServletRequest request);
}

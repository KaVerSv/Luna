package com.example.Luna.service;

import com.example.Luna.api.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CartService {
    public List<BookDto> getUserCart(@NonNull HttpServletRequest request);
    public void addToCart(@NonNull HttpServletRequest request,int id);
    public void removeFromCart(@NonNull HttpServletRequest request,int id);
}

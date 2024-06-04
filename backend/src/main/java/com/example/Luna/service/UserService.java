package com.example.Luna.service;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User getUser(HttpServletRequest request);
    String getUsername(HttpServletRequest request);
    Boolean isBookInWishlist(HttpServletRequest request, Integer bookId);
    void addToWishList(HttpServletRequest request, Integer bookId);
    void removeFromWishList(HttpServletRequest request, Integer bookId);
    Boolean isAdmin(HttpServletRequest request);
    List<BookDto> checkForActiveDiscountsOnWishList(HttpServletRequest request);
}

package com.example.Luna.service;

import com.example.Luna.api.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User getUser(@NonNull HttpServletRequest request);
    String getUsername(@NonNull HttpServletRequest request);
    Boolean isBookInWishlist(@NonNull HttpServletRequest request, Integer bookId);
    void addToWishList(@NonNull HttpServletRequest request, Integer bookId);
    void removeFromWishList(@NonNull HttpServletRequest request, Integer bookId);
    Boolean isAdmin(@NonNull HttpServletRequest request);
}

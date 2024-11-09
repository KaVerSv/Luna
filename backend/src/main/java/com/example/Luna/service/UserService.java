package com.example.Luna.service;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User getUser();
    String getUsername();
    Boolean isBookInWishlist(Integer bookId);
    void addToWishList(Integer bookId);
    void removeFromWishList(Integer bookId);
    Boolean isAdmin();
    List<BookDto> checkForActiveDiscountsOnWishList();
}

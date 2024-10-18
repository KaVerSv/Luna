package com.example.Luna.service.impl;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.dto.BookWithDiscountDto;
import com.example.Luna.api.dto.DiscountDto;
import com.example.Luna.api.mapper.BookMapper;
import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.User;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.repository.UserRepository;
import com.example.Luna.security.service.JwtService;
import com.example.Luna.security.service.RoleEnum;
import com.example.Luna.service.DiscountService;
import com.example.Luna.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private final JwtService jwtService;
    DiscountService discountService;

    @Override
    public User getUser(@NonNull HttpServletRequest request) {
        String username = decodeUsername(request.getHeader("Authorization"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        return user;
    }

    @Override
    public String getUsername(@NonNull HttpServletRequest request) {
        User user = getUser(request);
        if (user == null)
            throw new IllegalArgumentException("User not found");
        return user.getUsername();
    }

    @Override
    public Boolean isBookInWishlist(@NonNull HttpServletRequest request, Integer bookId) {
        User user = getUser(request);
        return user.getWishList().stream().anyMatch(book -> book.getId().equals(bookId));
    }

    @Override
    public void addToWishList(@NonNull HttpServletRequest request, Integer bookId) {
        User user = getUser(request);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + bookId));
        user.getWishList().add(book);
        userRepository.save(user);
    }

    @Override
    public void removeFromWishList(@NonNull HttpServletRequest request, Integer bookId) {
        User user = getUser(request);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + bookId));
        user.getWishList().remove(book);
        userRepository.save(user);
    }

    private String decodeUsername(String authHeader) {
        final String jwt = authHeader.substring(7);
        return jwtService.extractUsername(jwt);
    }

    public Boolean isAdmin(@NonNull HttpServletRequest request) {
        User user = getUser(request);
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(RoleEnum.ROLE_ADMIN.name()));
    }

    @Override
    public List<BookDto> checkForActiveDiscountsOnWishList(@NonNull HttpServletRequest request) {
        User user = getUser(request);
        List<Book> lista = discountService.getBooksOnActiveDiscount(new ArrayList<>(user.getWishList()));
        List<BookDto> bookDtos = lista.stream()
                .map(BookDto::new)
                .collect(Collectors.toList());

        return bookDtos;
    }
}

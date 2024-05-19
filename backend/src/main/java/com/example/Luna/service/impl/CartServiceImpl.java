package com.example.Luna.service.impl;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.exception.ItemAlreadyInCartException;
import com.example.Luna.api.mapper.BookMapper;
import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.User;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.repository.UserRepository;
import com.example.Luna.security.service.JwtService;
import com.example.Luna.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public List<BookDto> getUserCart(@NonNull HttpServletRequest request) {

        String username = decodeUsername(request.getHeader("Authorization"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        return user.getCart().stream()
                .map(BookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addToCart(@NonNull HttpServletRequest request,int id) {

        String username = decodeUsername(request.getHeader("Authorization"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Book not found with id: " + id));

        if(user.getCart().contains(book)) {
            throw new ItemAlreadyInCartException("Book already in cart");
        }

        user.getCart().add(book);
        userRepository.save(user);
    }

    @Override
    public void removeFromCart(HttpServletRequest request, int id) {
        String username = decodeUsername(request.getHeader("Authorization"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Book not found with id: " + id));

        user.getCart().remove(book);
        userRepository.save(user);
    }

    public BigDecimal getTotalPrice(@NonNull HttpServletRequest request) {

        String username = decodeUsername(request.getHeader("Authorization"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        return user.getCart().stream()
                .map(Book::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String decodeUsername(String authHeader) {
        final String jwt;

        jwt = authHeader.substring(7);
        return jwtService.extractUsername(jwt);
    }
}

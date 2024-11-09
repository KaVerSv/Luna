package com.example.Luna.service.impl;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.exception.ItemAlreadyInCartException;
import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.User;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.repository.UserRepository;
import com.example.Luna.security.service.JwtService;
import com.example.Luna.security.service.UserContextService;
import com.example.Luna.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserContextService userContextService;

    @Override
    public List<BookDto> getUserCart() {
        User user = userContextService.getCurrentUser();

        return user.getCart().stream()
                .map(BookDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void addToCart(int id) {
        User user = userContextService.getCurrentUser();

        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Book not found with id: " + id));

        if(user.getCart().contains(book)) {
            throw new ItemAlreadyInCartException("Book already in cart");
        }
        if(user.getBooks().contains(book)) {
            throw new ItemAlreadyInCartException("Book already owned");
        }

        user.getCart().add(book);
        userRepository.save(user);
    }

    @Override
    public void removeFromCart(int id) {
        User user = userContextService.getCurrentUser();

        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Book not found with id: " + id));

        user.getCart().remove(book);
        userRepository.save(user);
    }

    public BigDecimal getTotalPrice() {
        User user = userContextService.getCurrentUser();

        return user.getCart().stream()
                .map(Book::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addToLibrary() {
        User user = userContextService.getCurrentUser();

        user.getBooks().addAll(user.getCart());
        user.getCart().clear();
        userRepository.save(user);
    }
}

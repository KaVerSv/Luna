package com.example.Luna.service.impl;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.User;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.repository.UserRepository;
import com.example.Luna.security.service.RoleEnum;
import com.example.Luna.security.service.UserContextService;
import com.example.Luna.service.DiscountService;
import com.example.Luna.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final DiscountService discountService;
    private final UserContextService userContextService;

    @Override
    public User getUser() {
        return userContextService.getCurrentUser();
    }

    @Override
    public String getUsername() {
        return userContextService.getCurrentUsername();
    }

    @Override
    public Boolean isBookInWishlist( Integer bookId) {
        User user = userContextService.getCurrentUser();
        return user.getWishList().stream().anyMatch(book -> book.getId().equals(bookId));
    }

    @Override
    public void addToWishList(Integer bookId) {
        User user = userContextService.getCurrentUser();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + bookId));
        user.getWishList().add(book);
        userRepository.save(user);
    }

    @Override
    public void removeFromWishList(Integer bookId) {
        User user = userContextService.getCurrentUser();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + bookId));
        user.getWishList().remove(book);
        userRepository.save(user);
    }

    public Boolean isAdmin() {
        User user = userContextService.getCurrentUser();
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(RoleEnum.ROLE_ADMIN.name()));
    }

    @Override
    public List<BookDto> checkForActiveDiscountsOnWishList() {
        User user = userContextService.getCurrentUser();
        List<Book> list = discountService.getBooksOnActiveDiscount(new ArrayList<>(user.getWishList()));
        List<BookDto> bookDtos = list.stream()
                .map(BookDto::new)
                .collect(Collectors.toList());

        return bookDtos;
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return null;
    }

}

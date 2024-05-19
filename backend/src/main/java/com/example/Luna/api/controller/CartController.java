package com.example.Luna.api.controller;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.exception.ItemAlreadyInCartException;
import com.example.Luna.security.auth.AuthenticationRequest;
import com.example.Luna.service.CartService;
import com.example.Luna.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {

    private CartService cartService;

    @GetMapping()
    public ResponseEntity<List<BookDto>> getUserCart(@NonNull HttpServletRequest request) {
        List<BookDto> books = cartService.getUserCart(request);
        return ResponseEntity.ok(books);
    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> addToCart(@NonNull HttpServletRequest request, @PathVariable("id") int bookId) {
        try {
            cartService.addToCart(request, bookId);
        } catch (ItemAlreadyInCartException e) {
            throw new ItemAlreadyInCartException("Item already in cart");
        }
        return ResponseEntity.ok("book added successfully");
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeFromCart(@NonNull HttpServletRequest request, @PathVariable("id") int bookId) {
        cartService.removeFromCart(request, bookId);
        return ResponseEntity.ok("book deleted successfully");
    }

    @GetMapping("/price")
    public ResponseEntity<BigDecimal> getTotalPrice(@NonNull HttpServletRequest request) {
        BigDecimal totalPrice = cartService.getTotalPrice(request);
        return ResponseEntity.ok(totalPrice);
    }
}

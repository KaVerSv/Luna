package com.example.Luna.api.controller;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.service.CartService;
import com.example.Luna.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

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
    public void addToCart(@NonNull HttpServletRequest request, @PathVariable("id") int bookId) {
        cartService.addToCart(request, bookId);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFromCart(@NonNull HttpServletRequest request, @PathVariable("id") int bookId) {
        cartService.removeFromCart(request, bookId);
    }
}

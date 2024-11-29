package com.example.Luna.api.controller;

import com.example.Luna.api.dto.BookWithDiscountDto;
import com.example.Luna.api.exception.ItemAlreadyInCartException;
import com.example.Luna.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping()
    public ResponseEntity<List<BookWithDiscountDto>> getUserCart() {
        List<BookWithDiscountDto> books = cartService.getUserCartDto();
        return ResponseEntity.ok(books);
    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> addToCart(@PathVariable("id") int bookId) {
        try {
            cartService.addToCart(bookId);
        } catch (ItemAlreadyInCartException e) {
            throw new ItemAlreadyInCartException("Item already in cart");
        }
        return ResponseEntity.ok("book added successfully");
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeFromCart(@PathVariable("id") int bookId) {
        cartService.removeFromCart(bookId);
        return ResponseEntity.ok("book deleted successfully");
    }

    @GetMapping("/price")
    public ResponseEntity<BigDecimal> getTotalPrice() {
        BigDecimal totalPrice = cartService.getTotalPrice();
        return ResponseEntity.ok(totalPrice);
    }

}

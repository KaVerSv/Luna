package com.example.Luna.api.controller;

import com.example.Luna.api.dto.DiscountDto;
import com.example.Luna.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
@AllArgsConstructor
public class DiscountController {
    private DiscountService discountService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DiscountDto> getDiscount(@PathVariable("id") int bookId) {
        DiscountDto discountDto = discountService.getDiscountByBookId(bookId);
        return ResponseEntity.ok(discountDto);
    }

    /*
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DiscountDto> createDiscount(@RequestBody DiscountDto discountDto) {
        DiscountDto saved = discountService.createDiscount(discountDto);
        return ResponseEntity.ok(saved);
    }*/

    @PostMapping("/book/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DiscountDto> createDiscount(@PathVariable("bookId") int bookId, @RequestBody DiscountDto discountDto) {
        DiscountDto saved = discountService.createDiscountForBook(bookId, discountDto);
        return ResponseEntity.ok(saved);
    }
}

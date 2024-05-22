package com.example.Luna.api.controller;

import com.example.Luna.api.dto.DiscountDto;
import com.example.Luna.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

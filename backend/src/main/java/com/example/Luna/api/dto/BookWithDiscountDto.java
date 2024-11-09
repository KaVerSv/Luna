package com.example.Luna.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookWithDiscountDto {
    private BookDto book;
    private DiscountDto discount;
}
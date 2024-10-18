package com.example.Luna.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookWithDiscountDto {
    private BookDto book;
    private DiscountDto discount;
}
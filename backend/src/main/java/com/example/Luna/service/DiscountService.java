package com.example.Luna.service;

import com.example.Luna.api.dto.DiscountDto;

import java.util.List;

public interface DiscountService {
    public DiscountDto getDiscountByBookId(int id);
    public DiscountDto createDiscount(DiscountDto discountDto);
    public List<DiscountDto> createDiscounts(List<DiscountDto> discountDtos);
    public DiscountDto createDiscountForBook(int bookId, DiscountDto discountDto);
}

package com.example.Luna.service;

import com.example.Luna.api.dto.DiscountDto;

public interface DiscountService {
    public DiscountDto getDiscountByBookId(int id);
}

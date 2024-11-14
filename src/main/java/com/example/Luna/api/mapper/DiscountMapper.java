package com.example.Luna.api.mapper;

import com.example.Luna.api.dto.DiscountDto;
import com.example.Luna.api.model.Discount;

public class DiscountMapper {
    public static DiscountDto mapToDiscountDto(Discount discount) {
        return new DiscountDto(
                discount.getId(),
                discount.getPercentage(),
                discount.getStartDate(),
                discount.getEndDate()
        );
    }

    public static Discount mapToDiscount(DiscountDto discountdto) {
        return new Discount(
                discountdto.getId(),
                discountdto.getPercentage(),
                discountdto.getStartDate(),
                discountdto.getEndDate()
        );
    }
}

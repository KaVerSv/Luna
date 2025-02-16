package com.example.Luna.api.mapper;

import com.example.Luna.api.dto.DiscountDto;
import com.example.Luna.api.model.Discount;

public class DiscountMapper {
    public static DiscountDto mapToDiscountDto(Discount discount) {
        if (discount == null) {
            return null; // Możesz zwrócić null lub domyślny DiscountDto
        }
        return new DiscountDto(
                discount.getId(),
                discount.getPercentage(),
                discount.getStartDate(),
                discount.getEndDate(),
                discount.getName()
        );
    }

    public static Discount mapToDiscount(DiscountDto discountDto) {
        if (discountDto == null) {
            return null; // Możesz zwrócić null lub domyślny Discount
        }
        return new Discount(
                discountDto.getId(),
                discountDto.getPercentage(),
                discountDto.getStartDate(),
                discountDto.getEndDate(),
                discountDto.getName()
        );
    }
}

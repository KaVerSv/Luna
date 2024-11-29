package com.example.Luna.api.dto;

import com.example.Luna.api.model.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class OrderItemDto {
    private Long id;
    private BookDto bookDto;
    private DiscountDto discountDto; // The discount at the time of purchase, if any
    private BigDecimal priceAtPurchase; // The price after applying discount
}

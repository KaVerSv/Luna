package com.example.Luna.api.dto;

import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.Discount;
import com.example.Luna.api.model.Order;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class OrderItemDto {
    private Long id;
    private Order order;
    private Book book;
    private Discount discount; // The discount at the time of purchase, if any
    private BigDecimal priceAtPurchase; // The price after applying discount
}

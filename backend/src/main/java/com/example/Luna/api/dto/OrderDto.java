package com.example.Luna.api.dto;

import com.example.Luna.api.model.User;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Date orderDate;
    private User user;
    private boolean paid;
    private Set<OrderItemDto> orderItems;
}

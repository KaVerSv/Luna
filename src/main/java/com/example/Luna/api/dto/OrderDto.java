package com.example.Luna.api.dto;

import com.example.Luna.api.model.Order;
import com.example.Luna.api.model.User;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Date orderDate;
    private User user;
    private boolean paid;
    private String payUOrderId;
    private Set<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.paid = order.isPaid();

        this.orderItems = order.getOrderItems().stream().map(orderItem ->
                new OrderItemDto(
                        orderItem.getId(),
                        orderItem.getBook(),
                        orderItem.getDiscount(),
                        orderItem.getPriceAtPurchase()
                )
        ).collect(Collectors.toSet());
    }
}

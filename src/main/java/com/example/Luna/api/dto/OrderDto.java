package com.example.Luna.api.dto;

import com.example.Luna.api.mapper.DiscountMapper;
import com.example.Luna.api.model.Order;
import com.example.Luna.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public class OrderDto {
    private Long id;
    private Date orderDate;
    private boolean paid;
    private String payUOrderId;
    private Set<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.paid = order.isPaid();
        this.payUOrderId = order.getPayUOrderId();

        this.orderItems = order.getOrderItems().stream().map(orderItem -> {
            // Sprawdzenie, czy discount jest null i ustawienie odpowiedniego domyślnego DiscountDto
            DiscountDto discountDto = (orderItem.getDiscount() != null)
                    ? DiscountMapper.mapToDiscountDto(orderItem.getDiscount())
                    : null;  // Można także przypisać jakąś domyślną wartość, jeśli chcesz

            return new OrderItemDto(
                    orderItem.getId(),
                    new BookDto(orderItem.getBook()),
                    discountDto,
                    orderItem.getPriceAtPurchase()
            );
        }).collect(Collectors.toSet());
    }
}

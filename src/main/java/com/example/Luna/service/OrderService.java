package com.example.Luna.service;


import com.example.Luna.api.dto.*;
import com.example.Luna.api.mapper.DiscountMapper;
import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.Order;
import com.example.Luna.api.model.OrderItem;
import com.example.Luna.api.model.User;
import com.example.Luna.repository.OrderRepository;
import com.example.Luna.repository.UserRepository;
import com.example.Luna.security.service.UserContextService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PayUService payUService;
    private final DiscountService discountService;
    private final UserContextService userContextService;
    private final CartService cartService;

    @Transactional
    public String createOrderAndInitiatePayment() {
        User user = userContextService.getCurrentUser();
        Set<Book> books = user.getCart();

        //create Order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setPaid(false);

        //append books
        for (Book book : books) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(book);
            orderItem.setDiscount(discountService.getDiscountByBook(book));
            orderItem.setPriceAtPurchase(orderItem.calculateEffectivePrice());
            order.addOrderItem(orderItem);
        }

        //total amount in sub currency egz. cents
        int totalAmount = 0;
        // Calculate the total amount from the cart items
        for (OrderItem orderItem : order.getOrderItems()) {
            totalAmount += orderItem.getPriceAtPurchase().movePointRight(2).intValueExact();
        }

        //save order
        orderRepository.save(order);

        // Create a payment request to PayU
        PayURequest payURequest = new PayURequest(totalAmount, user.getEmail(), "Order payment");
        PayUResponse payUResponse = payUService.createPayment(payURequest, order.getId());

        //save transactionId order
        order.setPayUOrderId(payUResponse.getTransactionId());
        orderRepository.save(order);

        // Return the PayU payment URL for frontend to redirect the user
        return payUResponse.getPaymentUrl();
    }

    public void confirmOrder(Long userId, String payUTransactionId) {
        // Verify payment with PayU using the transaction ID
        boolean isPaymentConfirmed = payUService.verifyPayment(payUTransactionId);

        if (!isPaymentConfirmed) {
            throw new IllegalStateException("Payment not confirmed");
        }

        // Continue with order creation and save it to the database as in the original code
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Set order details here and save to repository
        // Find the latest order for the user that hasn't been marked as paid yet
        Order order = orderRepository.findTopByUserIdAndPaidFalseOrderByOrderDateDesc(userId)
                .orElseThrow(() -> new IllegalStateException("Unpaid order not found for user"));

        // Mark the order as paid
        order.setPaid(true);
        cartService.addToLibraryAndClear(user);
        orderRepository.save(order);
    }

    public List<OrderDto> getOrdersForUser(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("No orders for user"));

        return orders.stream().map(order -> {
            Set<OrderItemDto> orderItemDtos = order.getOrderItems().stream().map(orderItem -> {
                DiscountDto discountDto = orderItem.getDiscount() != null
                        ? DiscountMapper.mapToDiscountDto(orderItem.getDiscount())
                        : null;
                return new OrderItemDto(
                        orderItem.getId(),
                        new BookDto(orderItem.getBook()),
                        discountDto,
                        orderItem.getPriceAtPurchase()
                );
            }).collect(Collectors.toSet());

            return new OrderDto(
                    order.getId(),
                    order.getOrderDate(),
                    order.isPaid(),
                    order.getPayUOrderId(),
                    orderItemDtos
            );
        }).collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long id) {
        User user = userContextService.getCurrentUser();
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        //ensure order belongs to user
        if (!order.getUser().equals(user)) {
            throw new IllegalStateException("Order not found");
        }

        return new OrderDto(order);
    }
}
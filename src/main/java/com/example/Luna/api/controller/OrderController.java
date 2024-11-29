package com.example.Luna.api.controller;

import com.example.Luna.api.dto.OrderDto;
import com.example.Luna.api.model.User;
import com.example.Luna.security.service.UserContextService;
import com.example.Luna.service.CartService;
import com.example.Luna.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserContextService userContextService;
    private final CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrderAndInitiatePayment() {
        String paymentUrl = orderService.createOrderAndInitiatePayment();

        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", paymentUrl);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmOrder(@RequestParam("transactionId") String transactionId ){
        User user = userContextService.getCurrentUser();
        orderService.confirmOrder(user.getId(), transactionId);
        return ResponseEntity.ok("Order confirmed and saved");
    }

    @GetMapping()
    public List<OrderDto> getOrdersByUserId() {
        User user = userContextService.getCurrentUser();
        return orderService.getOrdersForUser(user.getId());
    }

    @GetMapping()
    public OrderDto getOrderById(@RequestParam("id") Long id) {
        return orderService.getOrderById(id);
    }
}
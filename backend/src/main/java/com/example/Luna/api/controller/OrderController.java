package com.example.Luna.api.controller;

import com.example.Luna.api.dto.OrderDto;
import com.example.Luna.api.dto.OrderRequestDto;
import com.example.Luna.api.model.User;
import com.example.Luna.security.service.UserContextService;
import com.example.Luna.service.OrderService;
import com.example.Luna.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserContextService userContextService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrderAndInitiatePayment(@RequestBody OrderRequestDto orderRequestDto) {
        User user = userContextService.getCurrentUser();
        String paymentUrl = orderService.createOrderAndInitiatePayment(orderRequestDto, user.getId());

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
}
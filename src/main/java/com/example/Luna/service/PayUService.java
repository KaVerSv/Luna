package com.example.Luna.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.Luna.api.dto.PayURequest;
import com.example.Luna.api.dto.PayUResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PayUService {

    private static final String PAYU_AUTH_URL = "https://secure.snd.payu.com/pl/standard/user/oauth/authorize";
    private static final String PAYU_ORDER_URL = "https://secure.snd.payu.com/api/v2_1/orders";

    @Value("${payu.clientId}")
    private String clientId;

    @Value("${payu.clientSecret}")
    private String clientSecret;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public PayUService(ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
    }

    // Method to fetch an access token from PayU
    private String getAccessToken() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("grant_type", "client_credentials");
            params.put("client_id", clientId);
            params.put("client_secret", clientSecret);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(PAYU_AUTH_URL))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(buildFormUrlEncodedString(params)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                Map<String, Object> responseBody = objectMapper.readValue(response.body(), Map.class);
                return (String) responseBody.get("access_token");
            } else {
                throw new RuntimeException("Failed to obtain access token from PayU");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting access token from PayU", e);
        }
    }

    // Helper method to build form URL encoded string
    private String buildFormUrlEncodedString(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!sb.isEmpty()) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }

    public PayUResponse createPayment(PayURequest payURequest, Long orderId) {
        try {
            String accessToken = getAccessToken();
            System.out.println("Access Token: " + accessToken);

            // Prepare the order payload
            Map<String, Object> order = new HashMap<>();
            order.put("notifyUrl", "http://localhost:8080/orders/confirm"); // URL for receiving status notifications
            order.put("continueUrl", "http://localhost:5173/confirm?id=" + orderId); //URL to check payment status after completion
            order.put("customerIp", "127.0.0.1"); // testing IP
            order.put("merchantPosId", clientId);
            order.put("description", "Opłata zamówienia");
            order.put("currencyCode", "PLN");

            int totalAmount = payURequest.getAmount(); // integer unit price in sub currency (cents)
            order.put("totalAmount", totalAmount);

            Map<String, Object> buyer = new HashMap<>();
            buyer.put("language", "pl");
            order.put("buyer", buyer);

            Map<String, Object> product = new HashMap<>();
            product.put("name", "Opłata zamówienia");
            product.put("unitPrice", totalAmount);
            product.put("quantity", 1);
            order.put("products", List.of(product));

            // Convert the order to JSON
            String orderJson = objectMapper.writeValueAsString(order);

            // Create HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(PAYU_ORDER_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + accessToken)
                    .POST(HttpRequest.BodyPublishers.ofString(orderJson))
                    .build();

            // Send HTTP request and capture response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Treat 200 (OK) and 302 (Found) as success responses
            if (response.statusCode() == HttpStatus.OK.value() || response.statusCode() == HttpStatus.FOUND.value()) {
                Map<String, Object> responseBody = objectMapper.readValue(response.body(), Map.class);
                String redirectUri = (String) responseBody.get("redirectUri");
                String transactionId = (String) responseBody.get("orderId");

                // Extract status as a LinkedHashMap and retrieve the statusCode string from it
                Map<String, Object> statusMap = (Map<String, Object>) responseBody.get("status");
                String status = (String) statusMap.get("statusCode");
                return new PayUResponse(redirectUri, transactionId, status);
            } else {
                throw new RuntimeException("Failed to create payment order with PayU. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Exception Message: " + e.getMessage());
            throw new RuntimeException("Error creating payment with PayU", e);
        }
    }

    // Method to verify payment status using transaction ID
    public boolean verifyPayment(String transactionId) {
        try {
            String accessToken = getAccessToken();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(PAYU_ORDER_URL + "/" + transactionId))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                Map<String, Object> responseBody = objectMapper.readValue(response.body(), Map.class);
                Map<String, Object> status = (Map<String, Object>) responseBody.get("status");
                String statusCode = (String) status.get("statusCode");
                return "SUCCESS".equals(statusCode);
            } else {
                throw new RuntimeException("Failed to verify payment with PayU");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error verifying payment with PayU", e);
        }
    }
}
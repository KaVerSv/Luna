package com.example.Luna.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayUResponse {
    private String paymentUrl; // URL to redirect the user to for payment
    private String transactionId; // Unique identifier for the transaction
    private String status; // Status of the payment request (e.g., SUCCESS, FAILURE)

    // Constructor
    public PayUResponse(String paymentUrl, String transactionId, String status) {
        this.paymentUrl = paymentUrl;
        this.transactionId = transactionId;
        this.status = status;
    }
}
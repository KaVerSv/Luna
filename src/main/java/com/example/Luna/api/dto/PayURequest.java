package com.example.Luna.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayURequest {
    private int amount;  // The amount to be paid in subcurrency egz. cents
    private String currency; // The currency type (e.g., "PLN" for Polish Zloty)
    private String email;    // The email of the user making the payment
    private String description; // A description of the transaction

    public PayURequest(int amount, String email, String description) {
        this.amount = amount;
        this.currency = "PLN"; // Set the default currency, change if needed
        this.email = email;
        this.description = description;
    }
}
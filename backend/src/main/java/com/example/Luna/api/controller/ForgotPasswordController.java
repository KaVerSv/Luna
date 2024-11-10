package com.example.Luna.api.controller;

import com.example.Luna.api.dto.ChangePassword;
import com.example.Luna.service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/passwordRecoveryRequest/{email}")
    public ResponseEntity<String> passwordRecoveryRequest(@PathVariable String email) {
        try {
            forgotPasswordService.generateAndSendOtp(email);
            return ResponseEntity.ok("Email sent for verification!");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.LOCKED);
        }
    }

    @PostMapping("/changePassword/{otp}/{email}")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword, @PathVariable String otp, @PathVariable String email) {
        try {
            forgotPasswordService.changePassword(otp, email, changePassword);
            return ResponseEntity.ok("Password changed!");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

}

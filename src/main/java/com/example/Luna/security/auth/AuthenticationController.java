package com.example.Luna.security.auth;

import io.swagger.v3.oas.annotations.Operation;
import com.example.Luna.security.auth.AuthenticationRequest;
import com.example.Luna.security.auth.AuthenticationResponse;
import com.example.Luna.security.auth.RegisterRequest;
import com.example.Luna.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @Operation(summary = "Register User",
            description = "Register user in application. Send mail for account confirmation")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
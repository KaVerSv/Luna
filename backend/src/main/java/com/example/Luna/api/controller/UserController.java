package com.example.Luna.api.controller;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/username")
    public ResponseEntity<String> getUserCart(@NonNull HttpServletRequest request) {
        String username = userService.getUsername(request);
        return ResponseEntity.ok(username);
    }

}

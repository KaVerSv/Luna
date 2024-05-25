package com.example.Luna.api.controller;

import com.example.Luna.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/wishList/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> onWishList(@NonNull HttpServletRequest request, @PathVariable("id") Integer id) {
        Boolean response = userService.isBookInWishlist(request,id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/wishList/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addToWishList(@NonNull HttpServletRequest request, @PathVariable("id") Integer id) {
        userService.addToWishList(request, id);
    }

    @DeleteMapping("/wishList/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFromWishList(@NonNull HttpServletRequest request, @PathVariable("id") Integer id) {
        userService.removeFromWishList(request, id);
    }

    @GetMapping("/admin")
    public ResponseEntity<Boolean> isAdmin(@NonNull HttpServletRequest request) {
        Boolean admin =  userService.isAdmin(request);
        return ResponseEntity.ok(admin);
    }
}

package com.example.Luna.api.controller;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/username")
    public ResponseEntity<String> getUserCart() {
        String username = userService.getUsername();
        return ResponseEntity.ok(username);
    }

    @GetMapping("/wishList/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> onWishList(@PathVariable("id") Integer id) {
        Boolean response = userService.isBookInWishlist(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/wishList/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addToWishList(@PathVariable("id") Integer id) {
        userService.addToWishList(id);
    }

    @DeleteMapping("/wishList/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFromWishList(@PathVariable("id") Integer id) {
        userService.removeFromWishList(id);
    }

    @GetMapping("/admin")
    public ResponseEntity<Boolean> isAdmin() {
        Boolean admin =  userService.isAdmin();
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/wishList")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> checkWishList() {
        return userService.checkForActiveDiscountsOnWishList();
    }
}

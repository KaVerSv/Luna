package com.example.Luna.api.controller;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.service.MessageProducer;
import com.example.Luna.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private final MessageProducer messageProducer;

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

    @GetMapping("/wishList")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> checkWishList(@NonNull HttpServletRequest request) {
        List<BookDto> books =  userService.checkForActiveDiscountsOnWishList(request);
        for (BookDto bookDto : books) {
            messageProducer.sendMessage(bookDto.getTitle() + " Book on you Wish List is on Discount!");
        }
        return books;
    }

    @GetMapping("/send")
    public String sendMessage() {
        String message = "Hello World";
        messageProducer.sendMessage(message);
        return "Message sent: " + message;
    }
}

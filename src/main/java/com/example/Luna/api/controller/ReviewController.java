package com.example.Luna.api.controller;

import com.example.Luna.api.dto.ReviewDTO;
import com.example.Luna.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("recentReviews")
    public ResponseEntity<List<ReviewDTO>> getBookReviews(@RequestParam Long id) {

        List<ReviewDTO> reviews = reviewService.getRecentReviews(id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ReviewDTO> postBookReview(@RequestParam String text, @RequestParam Boolean vote,@RequestParam Long bookId) {

        ReviewDTO review =  reviewService.addReview(text, vote, bookId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
}

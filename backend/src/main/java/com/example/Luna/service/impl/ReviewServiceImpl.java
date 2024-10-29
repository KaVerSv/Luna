package com.example.Luna.service.impl;

import com.example.Luna.api.dto.ReviewDTO;
import com.example.Luna.api.model.Review;
import com.example.Luna.api.model.User;
import com.example.Luna.repository.ReviewRepository;
import com.example.Luna.repository.UserRepository;
import com.example.Luna.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public List<ReviewDTO> getRecentReviews(Long bookId) {
        Timestamp thirtyDaysAgo = Timestamp.from(Instant.now().minusSeconds(30L * 24 * 60 * 60));

        List<Review> reviews = reviewRepository.findRecentReviewsByBookId(bookId, thirtyDaysAgo);

        return reviews.stream().limit(10).map(this::mapToReviewDTO).toList();
    }

    private ReviewDTO mapToReviewDTO(Review review) {
        User user = userRepository.findById(review.getUserId()).orElse(null);

        if (user == null) {
            return null;
        }

        int totalReviews = reviewRepository.countByUserId(user.getId());

        return new ReviewDTO(
                review.getId(),
                review.getVote(),
                review.getText(),
                review.getSqlTimestamp(),
                user.getId(),
                user.getUsername(),
                totalReviews
        );
    }
}

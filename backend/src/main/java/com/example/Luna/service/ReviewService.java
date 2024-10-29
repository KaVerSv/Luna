package com.example.Luna.service;

import com.example.Luna.api.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getRecentReviews(Long bookId);
}

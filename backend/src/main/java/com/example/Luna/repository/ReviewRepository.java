package com.example.Luna.repository;


import com.example.Luna.api.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r WHERE r.book.id = :bookId AND r.sqlTimestamp >= :thirtyDaysAgo ORDER BY r.sqlTimestamp DESC")
    List<Review> findRecentReviewsByBookId(@Param("bookId") Long bookId, @Param("thirtyDaysAgo") Timestamp thirtyDaysAgo);


    int countByUserId(Long User_id);
}

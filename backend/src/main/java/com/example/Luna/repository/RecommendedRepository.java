package com.example.Luna.repository;

import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.Recommended;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendedRepository extends JpaRepository<Recommended, Integer> {
    Optional<Recommended> findByName(String name);
}

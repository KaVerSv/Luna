package com.example.Luna.repository;

import com.example.Luna.api.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    Optional<Discount> findFirstByBooks_IdOrderByEndDateDesc(int bookId);


}
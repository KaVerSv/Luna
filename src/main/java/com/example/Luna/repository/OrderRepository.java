package com.example.Luna.repository;

import com.example.Luna.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findTopByUserIdAndPaidFalseOrderByOrderDateDesc(long userId);
    Optional<List<Order>> findByUserId(long userId);
}

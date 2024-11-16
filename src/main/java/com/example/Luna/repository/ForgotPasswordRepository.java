package com.example.Luna.repository;

import com.example.Luna.api.model.ForgotPassword;
import com.example.Luna.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {
    Optional<ForgotPassword> findByUser(User user);
}

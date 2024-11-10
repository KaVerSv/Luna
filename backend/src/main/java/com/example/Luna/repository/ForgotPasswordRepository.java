package com.example.Luna.repository;

import com.example.Luna.api.model.ForgotPassword;
import com.example.Luna.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

    @Query("select fp from ForgotPassword  fp where fp.otp = ?1 and fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(String otp, User user);

    Optional<ForgotPassword> findByUser(User user);
}

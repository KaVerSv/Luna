package com.example.Luna.repository;

import com.example.Luna.api.model.User;
import com.example.Luna.api.model.UserActivation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserActivationRepository extends JpaRepository<UserActivation, Long> {
    Optional<UserActivation> findByUser(User user);
}

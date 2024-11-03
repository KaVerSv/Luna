package com.example.Luna.repository;

import com.example.Luna.api.model.OldPassword;
import com.example.Luna.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OldPasswordRepository extends JpaRepository<OldPassword, Long> {

    Optional<List<OldPassword>> findByUser(User user);
}

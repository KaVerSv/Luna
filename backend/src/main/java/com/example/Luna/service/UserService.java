package com.example.Luna.service;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public User getUser(@NonNull HttpServletRequest request);
    public String getUsername(@NonNull HttpServletRequest request);
}

package com.example.Luna.service.impl;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.mapper.BookMapper;
import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.User;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.repository.UserRepository;
import com.example.Luna.security.service.JwtService;
import com.example.Luna.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public User getUser(@NonNull HttpServletRequest request) {
        String username = decodeUsername(request.getHeader("Authorization"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        return user;
    }

    @Override
    public String getUsername(@NonNull HttpServletRequest request) {
        String username = decodeUsername(request.getHeader("Authorization"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        return user.getUsername();
    }

    private String decodeUsername(String authHeader) {
        final String jwt;

        jwt = authHeader.substring(7);
        return jwtService.extractUsername(jwt);
    }


}

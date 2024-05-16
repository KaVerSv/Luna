package com.example.Luna.service.impl;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.mapper.BookMapper;
import com.example.Luna.api.model.User;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.repository.UserRepository;
import com.example.Luna.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LibraryServiceImpl {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private final JwtService jwtService;

    public List<BookDto>  getUserLibrary(@NonNull HttpServletRequest request) {
        String username = decodeUsername(request.getHeader("Authorization"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        return user.getBooks().stream()
                .map(BookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    private String decodeUsername(String authHeader) {
        final String jwt;

        jwt = authHeader.substring(7);
        return jwtService.extractUsername(jwt);
    }
}

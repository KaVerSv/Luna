package com.example.Luna.api.controller;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.service.LibraryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/library")
@AllArgsConstructor
public class LibraryController {

    private LibraryService libraryService;

    @GetMapping()
    public ResponseEntity<List<BookDto>> getLibrary(@NonNull HttpServletRequest request) {
        List<BookDto> books = libraryService.getUserLibrary(request);
        return ResponseEntity.ok(books);
    }

}

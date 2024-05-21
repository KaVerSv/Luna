package com.example.Luna.service;

import com.example.Luna.api.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;

import java.util.List;

public interface LibraryService {

    public List<BookDto> getUserLibrary(@NonNull HttpServletRequest request);
}

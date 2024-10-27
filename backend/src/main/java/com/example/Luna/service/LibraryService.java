package com.example.Luna.service;

import com.example.Luna.api.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.List;

public interface LibraryService {
    List<BookDto> getUserLibrary(@NonNull HttpServletRequest request);
    Resource getResource(@NonNull HttpServletRequest request, Long id) throws IOException;
}

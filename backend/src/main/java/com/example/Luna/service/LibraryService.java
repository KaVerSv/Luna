package com.example.Luna.service;

import com.example.Luna.api.dto.BookDto;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface LibraryService {
    List<BookDto> getUserLibrary();
    Resource getResource(Long id) throws IOException;
}

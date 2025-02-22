package com.example.Luna.api.controller;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/library")
@AllArgsConstructor
public class LibraryController {

    private LibraryService libraryService;

    @GetMapping()
    public ResponseEntity<List<BookDto>> getLibrary() {
        List<BookDto> books = libraryService.getUserLibrary();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws IOException {

        try {
            Resource resource = libraryService.getResource(id);
            String filename = resource.getFilename();

            String contentDisposition = "attachment; filename=\"" + filename + "\"";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}

package com.example.Luna.service.impl;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.User;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.security.service.UserContextService;
import com.example.Luna.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private BookRepository bookRepository;
    private final UserContextService userContextService;

    public List<BookDto> getUserLibrary() {
        User user = userContextService.getCurrentUser();

        return user.getBooks().stream()
                .map(BookDto::new)
                .collect(Collectors.toList());
    }

    public Resource getResource(Long id) throws IOException {
        User user = userContextService.getCurrentUser();

        Set<Book> books = user.getBooks();

        //checks if id is correct and if user owns passed id
        if (!books.contains(bookRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Book not found with id: " + id)
        ))) {
            throw new IllegalArgumentException("Book not owned by id: " + id);
        }

        Optional<Book> foundBook = books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();

        if (foundBook.isPresent()) {
            Book book = foundBook.get();
            String pathString = book.getPath();
            Path path = Paths.get(pathString);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found; please contact support");
            }
        } else {
            throw new IllegalArgumentException("Book not found with id: " + id);
        }
    }
}

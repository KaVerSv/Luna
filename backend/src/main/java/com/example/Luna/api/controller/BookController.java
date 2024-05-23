
package com.example.Luna.api.controller;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.dto.BookWithDiscountDto;
import com.example.Luna.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    //Build add book REST API
    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto savedBook = bookService.createBook(bookDto);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Build get book REST API
    @GetMapping("{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") int bookId) {
        BookDto bookDto = bookService.getBookById(bookId);
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<BookDto>> getFeaturedBooks(@RequestParam("name") String name) {
        List<BookDto> books = bookService.getFeaturedBooks(name);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/featuredD")
    public ResponseEntity<List<BookWithDiscountDto>> getFeaturedBooksD(@RequestParam("name") String name) {
        List<BookWithDiscountDto> books = bookService.getFeaturedBooksAndDiscounts(name);
        return ResponseEntity.ok(books);
    }
}

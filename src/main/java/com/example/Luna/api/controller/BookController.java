
package com.example.Luna.api.controller;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.dto.BookWithDiscountDto;
import com.example.Luna.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    //Build add book REST API
    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto savedBook = bookService.createBook(bookDto);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Build get book REST API
    @GetMapping("{id}")
    public ResponseEntity<BookWithDiscountDto> getBookById(@PathVariable("id") long bookId) {
        BookWithDiscountDto bookDto = bookService.getBookById(bookId);
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

    //simple search suggestions
    @GetMapping("search")
    public ResponseEntity<List<BookWithDiscountDto>> searchBooks(@RequestParam String keyword) {
        List<BookWithDiscountDto> books = bookService.searchByTitle(keyword);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    //advanced search
    @GetMapping("searchAdv")
    public ResponseEntity<List<BookWithDiscountDto>> searchBooks(
            @RequestParam String keyword,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) BigDecimal bottomPriceRange,
            @RequestParam(required = false) BigDecimal topPriceRange,
            @RequestParam(required = false) String sortOption,
            @RequestParam(required = false) Boolean specialOffersOnly,
            @RequestParam(required = false) List<String> languages,
            @RequestParam(required = false) List<String> tags) {

        List<BookWithDiscountDto> books = bookService.searchByTitle(keyword,
                pageNum, pageSize, bottomPriceRange, topPriceRange, sortOption, specialOffersOnly, languages, tags);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}

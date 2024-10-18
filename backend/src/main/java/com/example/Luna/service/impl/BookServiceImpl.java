package com.example.Luna.service.impl;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.dto.BookWithDiscountDto;
import com.example.Luna.api.dto.DiscountDto;
import com.example.Luna.api.exception.ResourceNotFoundException;
import com.example.Luna.api.mapper.BookMapper;
import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.Recommended;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.repository.RecommendedRepository;
import com.example.Luna.service.BookService;
import com.example.Luna.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private RecommendedRepository recommendedRepository;
    private DiscountService discountService;

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = BookMapper.mapToBook(bookDto);
        Book savedBook = bookRepository.save(book);
        return new BookDto(savedBook);
    }

    @Override
    public BookWithDiscountDto getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new ResourceNotFoundException("Book by id: " + bookId + " not found"));
        BookDto bookdto = new BookDto(book);
        DiscountDto discount = discountService.getDiscountByBookId(book.getId());
        return new BookWithDiscountDto(bookdto, discount);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(BookDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getFeaturedBooks(String name) {
        Recommended recommended = recommendedRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Recommended books by name: " + name + " not found"));

        Set<Book> books = recommended.getRecommended_tables();
        List<BookDto> bookDtos = books.stream()
                .map(BookDto::new)
                .collect(Collectors.toList());

        return bookDtos;
    }

    public List<BookWithDiscountDto> getFeaturedBooksAndDiscounts(String name) {

        Recommended recommended = recommendedRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Recommended books by name: " + name + " not found"));

        Set<Book> books = recommended.getRecommended_tables();
        List<BookDto> bookDtos = books.stream()
                .map(BookDto::new)
                .toList();

        return bookDtos.stream().map(book -> {
            DiscountDto discount = discountService.getDiscountByBookId(book.getId());
            return new BookWithDiscountDto(book, discount);
        }).collect(Collectors.toList());
    }

    public List<BookWithDiscountDto> searchByTitle(String title) {

        List<Book> books = bookRepository.findTop5ByTitleContainingIgnoreCase(title);
        List<BookDto> bookDtos = books.stream()
                .map(BookDto::new)
                .toList();

        return bookDtos.stream().map(book -> {
            DiscountDto discount = discountService.getDiscountByBookId(book.getId());
            return new BookWithDiscountDto(book, discount);
        }).collect(Collectors.toList());
    }

    public List<BookWithDiscountDto> searchByTitle(String keyword, Integer pageNum, Integer pageSize,
                                                   BigDecimal bottomPriceRange, BigDecimal topPriceRange,
                                                   String sortOption, Boolean specialOffersOnly,
                                                   List<String> languages, List<String> tags) {

        // Ustalanie sortowania
        Pageable pageable;
        switch (sortOption) {
            case "priceAsc" -> pageable = PageRequest.of(pageNum, pageSize, Sort.by("price").ascending());
            case "priceDesc" -> pageable = PageRequest.of(pageNum, pageSize, Sort.by("price").descending());
            case "releaseDate" -> pageable = PageRequest.of(pageNum, pageSize, Sort.by("publish_date").descending());
            case "userScore" -> pageable = PageRequest.of(pageNum, pageSize, Sort.by("user_score").descending());
            case null, default -> pageable = PageRequest.of(pageNum, pageSize);
        }

        // Ustalanie zakresu cen
        BigDecimal minPrice = bottomPriceRange != null ? bottomPriceRange : new BigDecimal("0.00");
        BigDecimal maxPrice = topPriceRange != null ? topPriceRange : new BigDecimal(Double.MAX_VALUE);

        // Wyszukiwanie książek z rabatem lub bez
        Page<Book> bookPage;
        if (Boolean.TRUE.equals(specialOffersOnly)) {
            bookPage = bookRepository.findByTitleContainingIgnoreCaseAndDiscountedPriceBetweenWithDiscountsAndLanguagesAndTags(
                    keyword, minPrice, maxPrice, languages, tags, pageable);
        } else {
            bookPage = bookRepository.findByTitleContainingIgnoreCaseAndDiscountedPriceBetweenAndLanguagesAndTags(
                    keyword, minPrice, maxPrice, languages, tags, pageable);
        }

        // Pobieranie listy książek
        List<Book> books = bookPage.getContent();

        // Tworzenie DTO z książek
        List<BookDto> bookDtos = books.stream()
                .map(BookDto::new)
                .toList();

        // Tworzenie listy książek z rabatem (jeśli dotyczy)
        List<BookWithDiscountDto> bookWithDiscountDtos = bookDtos.stream().map(book -> {
            DiscountDto discount = discountService.getDiscountByBookId(book.getId());
            return new BookWithDiscountDto(book, discount);
        }).toList();

        return bookWithDiscountDtos;
    }
}

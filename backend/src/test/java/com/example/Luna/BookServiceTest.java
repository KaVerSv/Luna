package com.example.Luna;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.dto.BookWithDiscountDto;
import com.example.Luna.api.dto.DiscountDto;
import com.example.Luna.api.mapper.BookMapper;
import com.example.Luna.api.model.Book;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.service.BookService;
import com.example.Luna.service.DiscountService;
import com.example.Luna.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private BookServiceImpl bookService;

    private List<Book> mockBooks;
    private DiscountDto mockDiscount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Przygotowanie danych testowych (mocków)
        Book book1 = new Book(1, "Test Book 1","test author","2002", "test desc",new BigDecimal("100.00"),"test location",1, 1);
        Book book2 = new Book(1, "Test Book 2","test author","2002", "test desc",new BigDecimal("100.00"),"test location",1, 1);
        mockBooks = List.of(book1, book2);

        List<BookDto> mockBookDtos = mockBooks.stream().map(BookMapper::mapToBookDto).toList();
        mockDiscount = new DiscountDto(1, 10, LocalDate.now().minusDays(5), LocalDate.now().plusDays(5));
    }

    @Test
    void shouldReturnBooksWithDiscountsAndFilters() {
        // Mockowanie zachowania repository
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(mockBooks);

        when(bookRepository.findByTitleContainingIgnoreCaseAndDiscountedPriceBetweenWithDiscountsAndLanguagesAndTags(
                eq("Test"), any(BigDecimal.class), any(BigDecimal.class), eq(List.of("English")), eq(List.of("History")), any(Pageable.class)))
                .thenReturn(bookPage);

        when(discountService.getDiscountByBookId(anyInt()))
                .thenReturn(mockDiscount);

        // Wywołanie metody do testowania
        List<BookWithDiscountDto> result = bookService.searchByTitle("Test", 0, 10, new BigDecimal("50.00"),
                new BigDecimal("300.00"), "priceAsc", true, List.of("English"), List.of("Science"));

        // Weryfikacja wyników
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(mockBooks.get(0).getTitle(), result.get(0).getBook().getTitle());
        assertEquals(mockDiscount, result.get(0).getDiscount());

        // Sprawdzenie, czy repository zostało wywołane z poprawnymi parametrami
        verify(bookRepository).findByTitleContainingIgnoreCaseAndDiscountedPriceBetweenWithDiscountsAndLanguagesAndTags(
                eq("Test"), eq(new BigDecimal("50.00")), eq(new BigDecimal("300.00")), eq(List.of("English")), eq(List.of("Science")), eq(pageable));
    }

    @Test
    void shouldReturnBooksWithoutDiscountsWhenSpecialOffersOnlyFalse() {
        // Mockowanie zachowania repository
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(mockBooks);

        when(bookRepository.findByTitleContainingIgnoreCaseAndDiscountedPriceBetweenAndLanguagesAndTags(
                eq("Test"), any(BigDecimal.class), any(BigDecimal.class), eq(List.of("English")), eq(List.of("Science")), any(Pageable.class)))
                .thenReturn(bookPage);

        when(discountService.getDiscountByBookId(anyInt()))
                .thenReturn(null); // Brak przeceny dla książki

        // Wywołanie metody do testowania
        List<BookWithDiscountDto> result = bookService.searchByTitle("Test", 0, 10, new BigDecimal("50.00"),
                new BigDecimal("300.00"), "priceAsc", false, List.of("English"), List.of("Science"));

        // Weryfikacja wyników
        assertNotNull(result);
        assertEquals(2, result.size());
        assertNull(result.get(0).getDiscount()); // Sprawdzamy, że nie ma rabatu

        // Sprawdzenie, czy repository zostało wywołane z poprawnymi parametrami
        verify(bookRepository).findByTitleContainingIgnoreCaseAndDiscountedPriceBetweenAndLanguagesAndTags(
                eq("Test"), eq(new BigDecimal("50.00")), eq(new BigDecimal("300.00")), eq(List.of("English")), eq(List.of("Science")), eq(pageable));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForInvalidSortOption() {
        // Sprawdzenie wyjątku dla niepoprawnego sortOption
        assertThrows(IllegalArgumentException.class, () ->
                bookService.searchByTitle("Test", 0, 10, new BigDecimal("50.00"),
                        new BigDecimal("300.00"), "invalidOption", false, List.of("English"), List.of("Science"))
        );
    }
}

package com.example.Luna.service.impl;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.exception.ResourceNotFoundException;
import com.example.Luna.api.mapper.BookMapper;
import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.Recommended;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.repository.RecommendedRepository;
import com.example.Luna.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private RecommendedRepository recommendedRepository;

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = BookMapper.mapToBook(bookDto);
        Book savedBook = bookRepository.save(book);
        return BookMapper.mapToBookDto(savedBook);
    }

    @Override
    public BookDto getBookById(int bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new ResourceNotFoundException("Book by id: " + bookId + " not found"));

        return BookMapper.mapToBookDto(book);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getFeaturedBooks(String name) {
        Recommended recommended = recommendedRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Recommended books by name: " + name + " not found"));

        Set<Book> books = recommended.getRecommended_tables();
        List<BookDto> bookDtos = books.stream()
                .map(BookMapper::mapToBookDto)
                .collect(Collectors.toList());

        return bookDtos;
    }
}

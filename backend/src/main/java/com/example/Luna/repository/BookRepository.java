package com.example.Luna.repository;

import com.example.Luna.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    boolean existsById(int id);
    Optional <Book> findById(int Id);
}

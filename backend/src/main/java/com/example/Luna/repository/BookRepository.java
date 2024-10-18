package com.example.Luna.repository;

import com.example.Luna.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional <Book> findById(int Id);

    //for suggestions in search
    List<Book> findTop5ByTitleContainingIgnoreCase(String title);

    @Query("SELECT DISTINCT b FROM Book b " +
            "JOIN b.discounts d " +
            "JOIN b.languages l " +
            "JOIN b.tags t " +
            "WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')) " +
            "AND (b.price * (1 - COALESCE(d.percentage, 0) / 100.0) BETWEEN :minPrice AND :maxPrice) " +
            "AND (:languages IS NULL OR l.language IN :languages) " +
            "AND (:tags IS NULL OR t.name IN :tags)")
    Page<Book> findByTitleContainingIgnoreCaseAndDiscountedPriceBetweenWithDiscountsAndLanguagesAndTags(
            @Param("title") String title,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("languages") List<String> languages,
            @Param("tags") List<String> tags,
            Pageable pageable);

    @Query("SELECT DISTINCT b FROM Book b " +
            "JOIN b.languages l " +
            "JOIN b.tags t " +
            "WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')) " +
            "AND (b.price BETWEEN :minPrice AND :maxPrice) " +
            "AND (:languages IS NULL OR l.language IN :languages) " +
            "AND (:tags IS NULL OR t.name IN :tags)")
    Page<Book> findByTitleContainingIgnoreCaseAndDiscountedPriceBetweenAndLanguagesAndTags(
            @Param("title") String title,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("languages") List<String> languages,
            @Param("tags") List<String> tags,
            Pageable pageable);
}

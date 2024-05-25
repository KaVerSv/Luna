package com.example.Luna.service.impl;

import com.example.Luna.api.dto.BookDto;
import com.example.Luna.api.dto.DiscountDto;
import com.example.Luna.api.exception.ResourceNotFoundException;
import com.example.Luna.api.mapper.BookMapper;
import com.example.Luna.api.mapper.DiscountMapper;
import com.example.Luna.api.model.Book;
import com.example.Luna.api.model.Discount;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.repository.DiscountRepository;
import com.example.Luna.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final BookRepository bookRepository;

    @Override
    public DiscountDto getDiscountByBookId(int bookId) {
        // Find the latest discount for the given book
        Discount discount = discountRepository.findFirstByBooks_IdOrderByEndDateDesc(bookId)
                .orElse(null);

        if (discount == null) {
            return null;
        }

        // Check if the discount has not ended
        LocalDate localDate = LocalDate.now();
        if (discount.getEndDate().isBefore(localDate)) {
            throw new ResourceNotFoundException("Book not on discount");
        }

        return DiscountMapper.mapToDiscountDto(discount);
    }

    @Override
    public DiscountDto createDiscount(DiscountDto discountDto) {
        Discount discount = DiscountMapper.mapToDiscount(discountDto);
        Discount savedDiscount = discountRepository.save(discount);
        return DiscountMapper.mapToDiscountDto(savedDiscount);
    }

    @Override
    public List<DiscountDto> createDiscounts(List<DiscountDto> discountDtos) {
        List<Discount> discounts = discountDtos.stream()
                .map(DiscountMapper::mapToDiscount)
                .collect(Collectors.toList());
        List<Discount> savedDiscounts = discountRepository.saveAll(discounts);
        return savedDiscounts.stream()
                .map(DiscountMapper::mapToDiscountDto)
                .collect(Collectors.toList());
    }

    @Override
    public DiscountDto createDiscountForBook(int bookId, DiscountDto discountDto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        Discount discount = DiscountMapper.mapToDiscount(discountDto);
        discount.getBooks().add(book);
        Discount savedDiscount = discountRepository.save(discount);

        book.getDiscounts().add(savedDiscount);
        bookRepository.save(book);

        return DiscountMapper.mapToDiscountDto(savedDiscount);
    }

}
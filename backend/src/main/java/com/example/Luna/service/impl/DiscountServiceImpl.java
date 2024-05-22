package com.example.Luna.service.impl;

import com.example.Luna.api.dto.DiscountDto;
import com.example.Luna.api.exception.ResourceNotFoundException;
import com.example.Luna.api.mapper.DiscountMapper;
import com.example.Luna.api.model.Discount;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.repository.DiscountRepository;
import com.example.Luna.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;

    @Override
    public DiscountDto getDiscountByBookId(int bookId) {
        // Find the latest discount for the given book
        Discount discount = discountRepository.findFirstByBooks_IdOrderByEndDateDesc(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not on discount"));

        // Check if the discount has not ended
        LocalDate localDate = LocalDate.now();
        if (discount.getEndDate().isBefore(localDate)) {
            throw new ResourceNotFoundException("Book not on discount");
        }

        return DiscountMapper.mapToDiscountDto(discount);
    }
}

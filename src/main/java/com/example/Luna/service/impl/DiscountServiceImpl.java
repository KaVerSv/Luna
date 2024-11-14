package com.example.Luna.service.impl;

import com.example.Luna.api.dto.DiscountDto;
import com.example.Luna.api.exception.ResourceNotFoundException;
import com.example.Luna.api.mapper.DiscountMapper;
import com.example.Luna.api.model.*;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.repository.DiscountRepository;
import com.example.Luna.repository.OrderRepository;
import com.example.Luna.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;

    @Override
    public Discount getDiscountByBook(Book book) {
        // Find the latest discount for the given book
        Discount discount = discountRepository.findFirstByBooks_IdOrderByEndDateDesc(book.getId())
                .orElse(null);

        if (discount == null) {
            return null;
        }

        // Check if the discount has not ended
        LocalDate localDate = LocalDate.now();
        if (discount.getEndDate().isBefore(localDate)) {
            throw new ResourceNotFoundException("Book not on discount");
        }

        return discount;
    }

    @Override
    public DiscountDto getDiscountDtoByBookId(long bookId) {
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
    public DiscountDto createDiscountForBook(long bookId, DiscountDto discountDto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        Discount discount = DiscountMapper.mapToDiscount(discountDto);
        discount.getBooks().add(book);
        Discount savedDiscount = discountRepository.save(discount);

        book.getDiscounts().add(savedDiscount);
        bookRepository.save(book);

        return DiscountMapper.mapToDiscountDto(savedDiscount);
    }

    @Override
    public List<Book> getBooksOnActiveDiscount(List<Book> books) {
        List<Book> booksOnActiveDiscount = new ArrayList<>();
        for (Book book : books) {
            Discount discount = discountRepository.findFirstByBooks_IdOrderByEndDateDesc(book.getId())
                    .orElse(null);
            if (discount != null && !discount.getEndDate().isBefore(LocalDate.now())) {
                booksOnActiveDiscount.add(book);
            }
        }
        return booksOnActiveDiscount;
    }

    public Order createOrder(User user, List<Book> books) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setPaid(false);

        for (Book book : books) {
            // Check for active discount
            Discount discount = discountRepository.findFirstByBooks_IdOrderByEndDateDesc(book.getId())
                    .filter(d -> !d.getEndDate().isBefore(LocalDate.now()))
                    .orElse(null);

            OrderItem orderItem = new OrderItem();
            orderItem.setBook(book);
            orderItem.setDiscount(discount);
            orderItem.setPriceAtPurchase(orderItem.calculateEffectivePrice());

            order.addOrderItem(orderItem);
        }

        return orderRepository.save(order);
    }
}
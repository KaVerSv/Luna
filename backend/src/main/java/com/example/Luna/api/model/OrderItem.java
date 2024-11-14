package com.example.Luna.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount; // The discount at the time of purchase, if any

    @Column(name = "price_at_purchase", nullable = false)
    private BigDecimal priceAtPurchase; // The price after applying discount

    // Calculate the effective price with discount applied, if available
    public BigDecimal calculateEffectivePrice() {
        if (discount != null) {
            BigDecimal discountMultiplier = BigDecimal.valueOf((100 - discount.getPercentage()) / 100.0);
            return book.getPrice().multiply(discountMultiplier);
        }
        return book.getPrice();
    }
}

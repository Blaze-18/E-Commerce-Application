package com.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One-to-One relationship with Order
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @NotNull
    @Column(name = "payment_date", updatable = false)
    private LocalDateTime paymentDate;

    @NotNull
    @Positive(message = "Payment amount must be greater than zero")
    @Column(nullable = false)
    private Double amount;

    @NotNull
    @Column(length = 50, nullable = false)
    private String paymentMethod; // e.g., "CARD", "CASH", "PAYPAL"

    @NotNull
    @Column(length = 20, nullable = false)
    private String status; // e.g., "COMPLETED", "PENDING", "FAILED"

    @PrePersist
    protected void onCreate() {
        paymentDate = LocalDateTime.now();
    }
}

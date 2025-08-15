package com.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    // Many Orders belong to one Customer
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    // One Order belongs to one Customer
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Payment payment;

    // One Order can have one Payment
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @NotBlank(message = "Order status cannot be blank")
    @NotNull(message = "Order status cannot be null")
    @Size(max = 20, message = "Order status must be less than 20 characters")
    @Column(nullable = false)
    private String status;

    @PositiveOrZero
    @NotNull
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "shipping_address", length = 255)
    private String shippingAddress;

    @NotNull
    @Column(name = "order_date", updatable = false)
    public LocalDateTime orderDate;

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
    }

}

package com.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50, message = "Username must be less than 50 characters")
    @NotNull
    @Column(nullable = false, length = 50)
    public String username;

    @NotBlank
    @Email
    @NotNull
    @Column(nullable = false, length = 100, unique = true)
    public String email;

    @NotBlank
    @NotNull
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    public String password;

    //Validation will be added later
    @Column
    public String address;

    @Column(unique = true)
    public String phoneNumber;

    @NotBlank(message = "Role cannot be blank")
    @Column(nullable = false)
    public String role;

    // One Customer can have multiple Orders
    @Builder.Default
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();


    @Column(name = "created_at", updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}

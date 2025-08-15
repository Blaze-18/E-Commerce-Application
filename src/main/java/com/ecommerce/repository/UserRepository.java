package com.ecommerce.repository;


import com.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Find user by their email when login and registration this will help
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    //Check if a user exists by the username for validation when registering a user or getting to their profile
    boolean existsByUsername(String username);

    //For the admin to see the customers
    List<User>  findByRole(String role);

    Optional<User> findByPhoneNumber(String phoneNumber);

    //Custom queries for users with order using JPQL
    @Query("SELECT DISTINCT u FROM User u JOIN u.orders o")
    List<User> findUsersWithOrders();

    // Counts how many customers are there
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    Long countByRole(@Param("role") String role);

}

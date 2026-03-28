package com.ecommerce.controller;

import com.ecommerce.dto.user.UserRegistrationDto;
import com.ecommerce.dto.user.UserResponseDto;
import com.ecommerce.dto.user.UserSummaryDto;
import com.ecommerce.dto.user.UserUpdateDto;
import com.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Register a new user
     * POST /api/users/register
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        log.info("Received registration request for email: {}", registrationDto.getEmail());

        UserResponseDto createdUser = userService.registerUser(registrationDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Get user by ID
     * GET /api/users/{id}
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        log.info("Received request to get user with ID: {}", id);

        UserResponseDto user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    /**
     * Get user by email
     * GET /api/users/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        log.info("Received request to get user with email: {}", email);

        UserResponseDto user = userService.getUserByEmail(email);

        return ResponseEntity.ok(user);
    }

    /**
     * Update user profile
     * PUT /api/users/{id}
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDto updateDto) {

        log.info("Received request to update user with ID: {}", id);

        UserResponseDto updatedUser = userService.updateUser(id, updateDto);

        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete user account
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Received request to delete user with ID: {}", id);

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Get all users (Admin only - add security later)
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<List<UserSummaryDto>> getAllUsers() {
        log.info("Received request to get all users");

        List<UserSummaryDto> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    /**
     * Get users by role
     * GET /api/users/role/{role}
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserSummaryDto>> getUsersByRole(@PathVariable String role) {
        log.info("Received request to get users with role: {}", role);

        List<UserSummaryDto> users = userService.getUsersByRole(role);

        return ResponseEntity.ok(users);
    }

    /**
     * Check if user exists
     * GET /api/users/{id}/exists
     */
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> userExists(@PathVariable Long id) {
        log.info("Checking if user exists with ID: {}", id);

        boolean exists = userService.userExists(id);

        return ResponseEntity.ok(exists);
    }
}
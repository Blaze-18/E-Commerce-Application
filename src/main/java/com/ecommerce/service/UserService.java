package com.ecommerce.service;

import com.ecommerce.dto.user.UserRegistrationDto;
import com.ecommerce.dto.user.UserResponseDto;
import com.ecommerce.dto.user.UserSummaryDto;
import com.ecommerce.dto.user.UserUpdateDto;
import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ConversionService conversionService;

    // Register User service

    public UserResponseDto registerUser (UserRegistrationDto userRegistrationDto) {
        log.info("Attempting to register user with email: {}", userRegistrationDto.getEmail());

        if(userRepository.existsByEmail(userRegistrationDto.getEmail())) {
            throw new RuntimeException("Email already exists : " + userRegistrationDto.getEmail());
        }

        User user =  User.builder()
                .username(userRegistrationDto.getUsername())
                .email(userRegistrationDto.getEmail())
                .password(userRegistrationDto.getPassword())
                .address(userRegistrationDto.getAddress())
                .phoneNumber(userRegistrationDto.getPhoneNumber())
                .role(userRegistrationDto.getRole())
                .build();

        User savedUser = userRepository.save(user);

        return convertToResponseDto(savedUser);
    }
    // Update user service
    public UserResponseDto updateUser(Long userId,UserUpdateDto userUpdateDto) {
        log.info("Attempting to update user with id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Check if new email is already taken by another user
        if (userUpdateDto.getEmail() != null && !userUpdateDto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userUpdateDto.getEmail())) {
                throw new RuntimeException("Email already exists: " + userUpdateDto.getEmail());
            }
        }

        // Update fields only if they are provided (not null)
        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
        }
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getAddress() != null) {
            user.setAddress(userUpdateDto.getAddress());
        }
        if (userUpdateDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        }

        User updatedUser = userRepository.save(user);
        log.info("Successfully updated user with ID: {}", userId);

        return convertToResponseDto(updatedUser);

    }

    public void deleteUser(Long userId) {
        log.info("Attempting to delete user with id: {}", userId);

        if(!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        userRepository.deleteById(userId);
        log.info("Successfully deleted user with id: {}", userId);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long userId) {
        log.info("Attempting to get user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        return convertToResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        log.info("Attempting to get user by email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return convertToResponseDto(user);
    }
    /**
     * Get all users (for admin)
     */
    @Transactional(readOnly = true)
    public List<UserSummaryDto> getAllUsers() {
        log.info("Fetching all users");

        return userRepository.findAll()
                .stream()
                .map(this::convertToSummaryDto)
                .collect(Collectors.toList());
    }
    /**
     * Get users by role
     */
    @Transactional(readOnly = true)
    public List<UserSummaryDto> getUsersByRole(String role) {
        log.info("Fetching users with role: {}", role);

        return userRepository.findByRole(role)
                .stream()
                .map(this::convertToSummaryDto)
                .collect(Collectors.toList());
    }

    /**
     * Check if user exists
     */
    @Transactional(readOnly = true)
    public boolean userExists(Long userId) {
        return userRepository.existsById(userId);
    }

    /**
     * Get user entity by ID (for internal use by other services)
     */
    @Transactional(readOnly = true)
    public User getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }
    //Helper Functions
    private UserResponseDto convertToResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
    private UserSummaryDto convertToSummaryDto(User user) {
        return UserSummaryDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}

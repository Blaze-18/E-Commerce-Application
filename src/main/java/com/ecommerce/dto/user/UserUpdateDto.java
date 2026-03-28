package com.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    @Size(max = 50, message = "Username must be less than 50 characters")
    private String username;

    @Email(message = "Please provide a valid email")
    private String email;

    private String address;

    private String phoneNumber;

    //Password updates will be handled separately for security purposes
}


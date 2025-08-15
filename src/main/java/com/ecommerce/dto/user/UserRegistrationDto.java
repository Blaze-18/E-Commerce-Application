package com.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    @NotBlank(message = "User Name can not be blank")
    @Size(max = 50, message = "User name can not exceed 50 characters")
    private String username;

    @NotBlank(message = "Email can not be blank")
    @Email(message = "Provide a valid mail address")
    private String email;

    @NotBlank(message = "Password can not be blank")
    @Size(min = 6, max = 100, message = "Password between 6 to 100 characters")
    private String password;

    private String address;

    private String phoneNumber;

    @NotBlank(message = "Role can not be left blank")
    private String role;
}

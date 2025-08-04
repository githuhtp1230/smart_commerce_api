package com.shop.smart_commerce_api.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username length must be longer than 3 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(min = 10, max = 15, message = "Numbers phone is required")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 3, message = "password length must be longer than 3 characters")
    private String password;
}

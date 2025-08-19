package com.shop.smart_commerce_api.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    private Integer id;

    private String name;

    @Email(message = "Email is invalid")
    private String email;

    @Size(min = 10, max = 15, message = "Numbers phone is invalid")
    private String phone;

    @Size(min = 3, message = "Password length must be longer than 3 characters")
    private String password;

    private String avatar;

    private Integer roleId;

    private Boolean isActive;
}

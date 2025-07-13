package com.shop.smart_commerce_api.dto.response.user;

import java.util.Set;

import com.shop.smart_commerce_api.entities.Address;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Integer id;
    private String email;
    private String name;
    private String avatar;
    private String phone;
    private String role;
    private Boolean isActive;
}

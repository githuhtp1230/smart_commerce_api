package com.shop.smart_commerce_api.dto.response.auth;

import com.shop.smart_commerce_api.dto.response.user.UserResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
}

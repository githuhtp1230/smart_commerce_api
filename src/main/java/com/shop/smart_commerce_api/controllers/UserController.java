package com.shop.smart_commerce_api.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.user.UserResponse;
import com.shop.smart_commerce_api.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/{userId}/toggle-is-active-user")
    public ApiResponse<UserResponse> toggleIsActiveUser(@PathVariable("userId") int userId) {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("User disabled successfully")
                .data(userService.toggleIsActiveUser(userId))
                .build();
    }
}

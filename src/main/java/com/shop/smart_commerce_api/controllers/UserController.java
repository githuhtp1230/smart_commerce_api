package com.shop.smart_commerce_api.controllers;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.filter.ProductSummaryFilterRequest;
import com.shop.smart_commerce_api.dto.request.filter.UserFilterRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.user.UserResponse;
import com.shop.smart_commerce_api.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers(@ModelAttribute UserFilterRequest filterRequest) {
        return ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .message("Get all users successfully")
                .data(userService.getAllUsers(filterRequest))
                .build();
    }
}

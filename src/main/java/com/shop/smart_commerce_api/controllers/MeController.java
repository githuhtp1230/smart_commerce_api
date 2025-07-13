package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.*;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.user.UserResponse;
import com.shop.smart_commerce_api.dto.request.user.UserUpdateProfileRequest;
import com.shop.smart_commerce_api.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/me")
public class MeController {
    private final UserService userService;

    @GetMapping("/profile")
    public ApiResponse<UserResponse> getProfile() {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Get profile successfully")
                .data(userService.getCurrentProfile())
                .build();
    }

    @PostMapping("/profile/update")
    public ApiResponse<UserResponse> updateProfile(@RequestBody UserUpdateProfileRequest request) {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Profile updated successfully")
                .data(userService.updateProfile(request))
                .build();
    }
}

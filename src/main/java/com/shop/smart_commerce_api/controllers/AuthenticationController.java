package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.auth.LoginRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.auth.LoginResponse;
import com.shop.smart_commerce_api.services.AuthenticationService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return ApiResponse.<LoginResponse>builder()
                .code(200)
                .message("Login successfully")
                .data(authenticationService.login(request))
                .build();
    }

}

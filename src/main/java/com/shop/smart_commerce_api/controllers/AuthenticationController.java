package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.auth.LoginRequest;
import com.shop.smart_commerce_api.dto.request.auth.RegisterRequest;
import com.shop.smart_commerce_api.dto.request.otp.RegisterOtpRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.auth.LoginResponse;
import com.shop.smart_commerce_api.services.AuthenticationService;

import jakarta.validation.Valid;
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

    @PostMapping("/register")
    public ApiResponse<LoginResponse> register(@RequestBody @Valid RegisterRequest request) {
        authenticationService.register(request);
        return ApiResponse.<LoginResponse>builder()
                .code(200)
                .message("Otp is sent to your email")
                .build();
    }

    @PostMapping("/register-verify-otp")
    public ApiResponse<?> registerVerifyOtp(@RequestBody RegisterOtpRequest request) {
        authenticationService.verifyOtp(request);
        return ApiResponse.<LoginResponse>builder()
                .code(200)
                .message("Verify otp successfully")
                .build();
    }

}

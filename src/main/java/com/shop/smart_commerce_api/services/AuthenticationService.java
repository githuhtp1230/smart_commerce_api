package com.shop.smart_commerce_api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.auth.LoginRequest;
import com.shop.smart_commerce_api.dto.response.auth.LoginResponse;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtGeneratorService jwtGeneratorService;

    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtGeneratorService.generateAccessToken(user);
            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.EMAIL_OR_PASSWORD_INVALID);
        }
    }
}

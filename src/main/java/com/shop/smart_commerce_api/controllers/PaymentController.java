package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.payment.PaymentResponse;
import com.shop.smart_commerce_api.dto.response.user.UserResponse;
import com.shop.smart_commerce_api.services.PaymentService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public ApiResponse<List<PaymentResponse>> getAllPayments() {
        return ApiResponse.<List<PaymentResponse>>builder()
                .code(200)
                .message("Get payments successfully")
                .data(paymentService.getAllPayments())
                .build();
    }

}

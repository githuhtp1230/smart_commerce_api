package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.category.CreateCategoryRequest;
import com.shop.smart_commerce_api.dto.request.checkout.CheckoutRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.category.CategoryResponse;
import com.shop.smart_commerce_api.services.CheckoutService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CheckoutRequest request) {
        checkoutService.checkout(request);
        return ApiResponse.<CategoryResponse>builder()
                .code(200)
                .message("Create category successfully")
                .build();
    }
}

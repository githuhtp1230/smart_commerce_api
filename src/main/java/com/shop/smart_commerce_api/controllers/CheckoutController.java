package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.category.CreateCategoryRequest;
import com.shop.smart_commerce_api.dto.request.checkout.CheckoutRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.category.CategoryResponse;
import com.shop.smart_commerce_api.dto.response.checkout.CheckoutResponse;
import com.shop.smart_commerce_api.services.CheckoutService;
import com.shop.smart_commerce_api.util.RequestUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public ApiResponse<CheckoutResponse> checkoutTransfer(@RequestBody CheckoutRequest request,
            HttpServletRequest servletRequest) {
        request.setIpAddress(RequestUtil.getIpAddress(servletRequest));
        return ApiResponse.<CheckoutResponse>builder()
                .code(200)
                .message("Checkout successfully")
                .data(checkoutService.checkoutTransfer(request))
                .build();
    }

}

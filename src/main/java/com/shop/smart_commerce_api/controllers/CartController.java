package com.shop.smart_commerce_api.controllers;

import com.shop.smart_commerce_api.dto.request.cart.AddCartItemRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.cart.CartItemResponse;
import com.shop.smart_commerce_api.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ApiResponse<List<CartItemResponse>> getCartItems() {
        return ApiResponse.<List<CartItemResponse>>builder()
                .code(200)
                .message("Get cart successfully")
                .data(cartService.getCart())
                .build();
    }

    @PostMapping("/add-item")
    public ApiResponse<CartItemResponse> addItem(@RequestBody AddCartItemRequest request) {
        return ApiResponse.<CartItemResponse>builder()
                .code(200)
                .message("Add item to cart successfully")
                .data(cartService.addItem(request))
                .build();
    }

}

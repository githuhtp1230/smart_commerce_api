package com.shop.smart_commerce_api.controllers;

import com.shop.smart_commerce_api.dto.request.cart.AddCartItemRequest;
import com.shop.smart_commerce_api.dto.request.cart.DeleteMultipleCartItemsRequest;
import com.shop.smart_commerce_api.dto.request.cart.UpdateCartItemQuantityRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.cart.CartItemResponse;
import com.shop.smart_commerce_api.dto.response.cart.UpdateCartItemQuantityResponse;
import com.shop.smart_commerce_api.services.CartService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/cart/items")
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

    @PostMapping
    public ApiResponse<CartItemResponse> addItem(@RequestBody AddCartItemRequest request) {
        return ApiResponse.<CartItemResponse>builder()
                .code(200)
                .message("Add item to cart successfully")
                .data(cartService.addItem(request))
                .build();
    }

    @DeleteMapping("/{cartItemId}")
    public ApiResponse<?> deleteCartItem(@PathVariable Integer cartItemId) {
        cartService.deleteItem(cartItemId);
        return ApiResponse.<CartItemResponse>builder()
                .code(200)
                .message("Delete item from cart successfully")
                .build();
    }

    @DeleteMapping
    public ApiResponse<?> deleteMultipleCartItem(@RequestBody DeleteMultipleCartItemsRequest request) {
        cartService.deleteMultipleCartItem(request);
        return ApiResponse.<CartItemResponse>builder()
                .code(200)
                .message("Delete all items from cart successfully")
                .build();
    }

    @PostMapping("/{cartItemId}")
    public ApiResponse<UpdateCartItemQuantityResponse> changeQuantity(
            @RequestBody UpdateCartItemQuantityRequest request, @PathVariable Integer cartItemId) {
        return ApiResponse.<UpdateCartItemQuantityResponse>builder()
                .code(200)
                .message("Update quantity cart item successfully")
                .data(cartService.changeQuantity(request.getChange(), cartItemId))
                .build();
    }
}

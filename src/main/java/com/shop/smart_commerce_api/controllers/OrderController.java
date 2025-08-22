package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.*;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.dto.response.order.OrdersByStatusResponse;
import com.shop.smart_commerce_api.entities.Order;
import com.shop.smart_commerce_api.dto.request.order.UpdateStatusOrderRepuest;
import com.shop.smart_commerce_api.services.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{id}")
    public ApiResponse<OrderResponse> updateOrderStatus(@PathVariable Integer id,
            @RequestBody UpdateStatusOrderRepuest request) {
        OrderResponse response = orderService.updateOrderStatus(id, request.getStatus());
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Order status updated successfully")
                .data(response)
                .build();
    }
}

package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.dto.response.order.OrdersByStatusResponse;
import com.shop.smart_commerce_api.entities.Order;
import com.shop.smart_commerce_api.services.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders-by-status")
    public ApiResponse<OrdersByStatusResponse> getMyOrdersByStatus() {
        OrdersByStatusResponse data = orderService.getMyOrdersByStatus();
        return ApiResponse.<OrdersByStatusResponse>builder()
                .code(200)
                .message("Orders fetched successfully")
                .data(data)
                .build();
    }
}

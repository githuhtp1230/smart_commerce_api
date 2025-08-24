package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.PageResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderSummaryResponse;
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

    @GetMapping
    public ApiResponse<PageResponse<OrderSummaryResponse>> getAllOrder(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {

        return ApiResponse.<PageResponse<OrderSummaryResponse>>builder()
                .code(200)
                .data(orderService.getAllOrders(status, page, limit))
                .build();
    }

    @GetMapping("/stats")
    public ApiResponse<Map<String, Long>> getOrderStats() {
        ApiResponse<Map<String, Long>> response = ApiResponse.<Map<String, Long>>builder()
                .code(200)
                .message("Success")
                .data(orderService.getOrderStats())
                .build();
        return response;
    }

    @PutMapping("/{orderId}/status")
    public ApiResponse<OrderSummaryResponse> updateOrderStatus(
            @PathVariable Integer orderId,
            @RequestParam String status) {
        OrderSummaryResponse updateOrderResponse = orderService.updateOrderStatus(orderId, status);
        return ApiResponse.<OrderSummaryResponse>builder()
                .data(updateOrderResponse)
                .code(200)
                .build();
    }

}

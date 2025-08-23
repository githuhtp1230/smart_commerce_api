package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.*;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.order.CategoryStatisticalResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.dto.response.order.OrdersByStatusResponse;
import com.shop.smart_commerce_api.dto.response.order.ProductStatisticalResponse;
import com.shop.smart_commerce_api.entities.Order;
import com.shop.smart_commerce_api.dto.request.order.CategoryStatisticalRequest;
import com.shop.smart_commerce_api.dto.request.order.ProductStatisticalRequest;
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

    @GetMapping({ "/statistical/product/{productId}", "/statistical/product" })
    public ApiResponse<ProductStatisticalResponse> getProductStatistical(
            @PathVariable(value = "productId", required = false) Integer productId,
            @RequestParam(required = false) String time) {
        ProductStatisticalRequest request = new ProductStatisticalRequest();
        request.setProductId((productId != null && productId != 0) ? productId : null);
        request.setTime(time);
        ProductStatisticalResponse result = orderService.getProductStatistical(request);
        return ApiResponse.<ProductStatisticalResponse>builder()
                .code(200)
                .message("Product statistical data fetched successfully")
                .data(result)
                .build();
    }

    @GetMapping("/statistical/category/{categoryId}")
    public ApiResponse<CategoryStatisticalResponse> getCategoryStatistical(@PathVariable Integer categoryId,
            @RequestParam(required = false) String time) {
        CategoryStatisticalRequest request = new CategoryStatisticalRequest();
        request.setCategoryId(categoryId);
        request.setTime(time);
        CategoryStatisticalResponse result = orderService.getCategoryStatistical(request);
        return ApiResponse.<CategoryStatisticalResponse>builder()
                .code(200)
                .message("Category statistical data fetched successfully")
                .data(result)
                .build();
    }
}

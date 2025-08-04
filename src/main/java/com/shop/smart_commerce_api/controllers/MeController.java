package com.shop.smart_commerce_api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderDetailResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.dto.response.user.UserResponse;
import com.shop.smart_commerce_api.dto.request.filter.OrderFilterRequest;
import com.shop.smart_commerce_api.dto.request.order.AddOrderDetailRequest;
import com.shop.smart_commerce_api.dto.request.user.UserUpdateProfileRequest;
import com.shop.smart_commerce_api.services.OrderDetailService;
import com.shop.smart_commerce_api.services.OrderService;
import com.shop.smart_commerce_api.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/me")
public class MeController {
        private final UserService userService;
        private final OrderService orderService;
        private final OrderDetailService orderDetailService;

        @GetMapping("/profile")
        public ApiResponse<UserResponse> getProfile() {
                return ApiResponse.<UserResponse>builder()
                                .code(200)
                                .message("Get profile successfully")
                                .data(userService.getCurrentProfile())
                                .build();
        }

        @PostMapping("/profile/update")
        public ApiResponse<UserResponse> updateProfile(@RequestBody UserUpdateProfileRequest request) {
                return ApiResponse.<UserResponse>builder()
                                .code(200)
                                .message("Profile updated successfully")
                                .data(userService.updateProfile(request))
                                .build();
        }

        @GetMapping("/orders")
        public ApiResponse<List<OrderResponse>> getMyOrders() {
                return ApiResponse.<List<OrderResponse>>builder()
                                .code(200)
                                .message("Orders retrieved successfully")
                                .data(orderService.getMyOrders())
                                .build();
        }

        @GetMapping("/order-details/{orderId}")
        public ApiResponse<List<OrderDetailResponse>> getOrderDetails(@PathVariable Integer orderId) {
                return ApiResponse.<List<OrderDetailResponse>>builder()
                                .code(200)
                                .message("Order details retrieved successfully")
                                .data(orderDetailService.getOrderDetails(orderId))
                                .build();
        }

        @PostMapping("/order-details")
        public ApiResponse<OrderDetailResponse> addOrderDetail(@RequestBody AddOrderDetailRequest request) {
                OrderDetailResponse addedOrderDetail = orderDetailService.addOrderDetail(request);
                return ApiResponse.<OrderDetailResponse>builder()
                                .code(201)
                                .message("Order detail added successfully")
                                .data(addedOrderDetail)
                                .build();
        }

        @GetMapping("/orders/filter")
        public ApiResponse<List<OrderResponse>> filterOrders(
                        @ModelAttribute OrderFilterRequest filterRequest) {

                List<OrderResponse> orders = orderService.filterOrders(filterRequest);

                return ApiResponse.<List<OrderResponse>>builder()
                                .code(200)
                                .message("Orders filtered successfully")
                                .data(orders)
                                .build();
        }
}

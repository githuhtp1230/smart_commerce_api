package com.shop.smart_commerce_api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.PageResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderDetailResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderSummaryResponse;
import com.shop.smart_commerce_api.dto.response.user.UserResponse;
import com.shop.smart_commerce_api.entities.User;
import com.cloudinary.Api;
import com.shop.smart_commerce_api.dto.request.auth.MailContactRequest;
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

        @GetMapping("/orders")
        public ApiResponse<PageResponse<OrderSummaryResponse>> getOrders(
                        @RequestParam(required = false) String status,
                        @RequestParam int page,
                        @RequestParam int limit) {

                if (page < 1)
                        page = 1;

                PageResponse<OrderSummaryResponse> result = orderService.getOrderSummaries(status, page - 1, limit,
                                userService.getCurrentUser().getId());

                return ApiResponse.<PageResponse<OrderSummaryResponse>>builder()
                                .code(200)
                                .message("Get orders successfully")
                                .data(result)
                                .build();
        }

        @PostMapping("send-email-contact")
        public ApiResponse<String> postMethodName(@RequestBody MailContactRequest request) {
                userService.sendEmailContact(request);
                return ApiResponse.<String>builder()
                                .code(200)
                                .message("Email sent successfully")
                                .data("Email sent to: " + request.getTitle())
                                .build();
        }

        @PostMapping("/order/{id}/cancel")
        public ApiResponse<OrderResponse> cancelOrder(@PathVariable Integer id) {
                OrderResponse response = orderService.cancelOrderByUser(id);
                return ApiResponse.<OrderResponse>builder()
                                .code(200)
                                .message("Order cancelled successfully")
                                .data(response)
                                .build();
        }

}

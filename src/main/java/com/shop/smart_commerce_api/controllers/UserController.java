package com.shop.smart_commerce_api.controllers;

import lombok.RequiredArgsConstructor;

import java.util.List;

import com.shop.smart_commerce_api.configurations.security.HasPermission;
import com.shop.smart_commerce_api.constant.Permissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.filter.ProductSummaryFilterRequest;
import com.shop.smart_commerce_api.dto.request.filter.UserFilterRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.PageResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderSummaryResponse;
import com.shop.smart_commerce_api.dto.response.user.UserResponse;
import com.shop.smart_commerce_api.services.OrderService;
import com.shop.smart_commerce_api.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

import com.shop.smart_commerce_api.dto.request.user.CreateUserRequest;
import com.shop.smart_commerce_api.dto.request.user.UpdateUserRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping("/{userId}/toggle-is-active-user")
    @HasPermission(Permissions.USER_UPDATE)
    public ApiResponse<UserResponse> toggleIsActiveUser(@PathVariable("userId") int userId) {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("User disabled successfully")
                .data(userService.toggleIsActiveUser(userId))
                .build();
    }

    @GetMapping
    @HasPermission(Permissions.USER_READ)
    public ApiResponse<List<UserResponse>> getAllUsers(@ModelAttribute UserFilterRequest filterRequest) {
        return ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .message("Get all users successfully")
                .data(userService.getAllUsers(filterRequest))
                .build();
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Create user successfully")
                .data(userService.createUser(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Integer id,           // Nhận id từ URL
            @RequestBody UpdateUserRequest request) {

        request.setId(id);                     // Gán id vào request
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Update user successfully")
                .data(userService.updateUser(request))
                .build();
    }

    @GetMapping("/{userId}/orders")
    public ApiResponse<PageResponse<OrderSummaryResponse>> getUserOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(required = false) String status) {

        if (page < 1)
            page = 1;

        return ApiResponse.<PageResponse<OrderSummaryResponse>>builder()
                .code(200)
                .message("Get user orders successfully")
                .data(orderService.getOrderSummaries(status, page - 1, limit, userId))
                .build();
    }

}

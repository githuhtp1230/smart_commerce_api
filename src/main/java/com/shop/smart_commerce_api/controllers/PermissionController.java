package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.permission.PermissionResponse;
import com.shop.smart_commerce_api.services.PermissionService;

import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    @GetMapping("/getAll")
    ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(200)
                .message("Permissions retrieved successfully")
                .data(permissionService.getPermissions())
                .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getPermissionsByRole(
            @RequestParam("role") Integer roleId) {

        List<PermissionResponse> permissions = permissionService.getPermissionsByRole(roleId);

        return ApiResponse.<List<PermissionResponse>>builder()
                .code(200)
                .message("Permissions retrieved successfully")
                .data(permissions)
                .build();
    }
}

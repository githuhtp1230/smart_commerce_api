package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.rolePremission.AddPermissionToRoleRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.services.RolePermissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/role-permissions")
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    @PostMapping("/add")
    public ApiResponse<String> addPermissionToRole(@RequestBody AddPermissionToRoleRequest request) {
        rolePermissionService.addPermissionToRole(request);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Permission added to role successfully")
                .build();
    }
}

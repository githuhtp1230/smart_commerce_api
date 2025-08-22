package com.shop.smart_commerce_api.controllers;

import com.shop.smart_commerce_api.dto.request.role.EnabledRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.permission.PermissionResponse;
import com.shop.smart_commerce_api.services.PermissionService;
import com.shop.smart_commerce_api.services.RoleService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final PermissionService permissionService;

    @PostMapping("/{roleId}/permissions/{permissionId}")
    public ApiResponse<Void> updateRolePermission(
            @PathVariable Integer roleId,
            @PathVariable Integer permissionId,
            @RequestBody EnabledRequest request) {
        roleService.updateRolePermission(roleId, permissionId, request.isEnabled());
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Role permission updated successfully")
                .build();
    }

    @GetMapping("/{roleId}/perrmissons")
    public ApiResponse<List<PermissionResponse>> getPermissionsByRole(
            @PathVariable Integer roleId) {
        List<PermissionResponse> permissions = permissionService.getPermissionsByRole(roleId);
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(200)
                .message("Permissions retrieved successfully")
                .data(permissions)
                .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAllPermissions() {
        List<PermissionResponse> permissions = permissionService.getPermissions();
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(200)
                .message("All permissions retrieved successfully")
                .data(permissions)
                .build();
    }

}
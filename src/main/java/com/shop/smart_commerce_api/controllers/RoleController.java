package com.shop.smart_commerce_api.controllers;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/{roleId}/permissions")
    public ApiResponse<Void> updateRolePermission(
            @PathVariable Integer roleId,
            @RequestParam Integer permissionId,
            @RequestParam boolean enabled) {
        roleService.updateRolePermission(roleId, permissionId, enabled);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Role permission updated successfully")
                .build();
    }

}

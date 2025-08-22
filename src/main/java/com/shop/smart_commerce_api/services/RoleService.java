package com.shop.smart_commerce_api.services;

import com.shop.smart_commerce_api.dto.request.role.EnabledRequest;
import com.shop.smart_commerce_api.entities.Permission;
import com.shop.smart_commerce_api.entities.Role;
import com.shop.smart_commerce_api.entities.RolePermission;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.repositories.PermissionRepository;
import com.shop.smart_commerce_api.repositories.RolePermissionRepository;
import com.shop.smart_commerce_api.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Transactional
    public void updateRolePermission(Integer roleId, Integer permissionId, boolean enabled) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        if (enabled) {
            boolean exists = role.getRolePermissions().stream()
                    .anyMatch(rp -> rp.getPermission().getId().equals(permissionId));
            if (!exists) {
                RolePermission rp = new RolePermission();
                rp.setRole(role);
                rp.setPermission(permission);
                rolePermissionRepository.save(rp);
            }
        } else {
            role.getRolePermissions().stream()
                    .filter(rp -> rp.getPermission().getId().equals(permissionId))
                    .findFirst()
                    .ifPresent(rolePermissionRepository::delete);
        }
    }
}
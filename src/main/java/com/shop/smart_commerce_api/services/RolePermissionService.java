package com.shop.smart_commerce_api.services;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.rolePremission.AddPermissionToRoleRequest;
import com.shop.smart_commerce_api.entities.Permission;
import com.shop.smart_commerce_api.entities.Role;
import com.shop.smart_commerce_api.entities.RolePermission;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.repositories.PermissionRepository;
import com.shop.smart_commerce_api.repositories.RolePermissionRepository;
import com.shop.smart_commerce_api.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public void addPermissionToRole(AddPermissionToRoleRequest request) {
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        Permission permission = permissionRepository.findById(request.getPermissionId())
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        boolean exists = rolePermissionRepository.existsByRoleIdAndPermissionId(
                request.getRoleId(), request.getPermissionId());

        if (exists) {
            throw new AppException(ErrorCode.PERMISSION_ALREADY_ASSIGNED);
        }

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);

        rolePermissionRepository.save(rolePermission);
    }
}

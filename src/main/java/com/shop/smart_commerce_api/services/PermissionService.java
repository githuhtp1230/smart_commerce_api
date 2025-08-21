package com.shop.smart_commerce_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.response.permission.PermissionResponse;
import com.shop.smart_commerce_api.mapper.PermissionMapper;
import com.shop.smart_commerce_api.repositories.PermissionRepository;
import com.shop.smart_commerce_api.entities.Permission;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public List<PermissionResponse> getPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    public List<PermissionResponse> getPermissionsByRole(Integer roleId) {
        List<Permission> permissions = permissionRepository.findPermissionsByRoleId(roleId);
        return permissions.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }
}
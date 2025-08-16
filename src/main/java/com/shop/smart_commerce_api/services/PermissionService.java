package com.shop.smart_commerce_api.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.response.permission.PermissionResponse;
import com.shop.smart_commerce_api.mapper.PermissionMapper;
import com.shop.smart_commerce_api.repositories.PermissionRepository;
import com.shop.smart_commerce_api.repositories.UserRepository;
import com.cloudinary.api.exceptions.ApiException;
import com.shop.smart_commerce_api.dto.request.permission.PermissionRequest;
import com.shop.smart_commerce_api.entities.Permission;
import com.shop.smart_commerce_api.entities.Role;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {
        private final PermissionRepository permissionRepository;
        private final PermissionMapper permissionMapper;
        private final UserService userService;
        private final UserRepository userRepository;

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

        // public List<PermissionResponse> getPermissionsByName(String name) {
        // var permissions = permissionRepository.findByName(name);
        // return permissions.stream()
        // .map(permissionMapper::toPermissionResponse)
        // .toList();
        // }

        // public PermissionResponse createPermission(PermissionRequest request) {
        // Permission permission = new Permission();
        // permission.setName(request.getName());
        // permission.setValue(request.getValue());
        // Permission saved = permissionRepository.save(permission);
        // return permissionMapper.toPermissionResponse(saved);
        // }

        // public PermissionResponse updatePermission(Integer id, PermissionRequest
        // request) {
        // Permission permission = permissionRepository.findById(id)
        // .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        // permission.setName(request.getName());
        // permission.setValue(request.getValue());
        // Permission updated = permissionRepository.save(permission);
        // return permissionMapper.toPermissionResponse(updated);
        // }
}

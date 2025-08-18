package com.shop.smart_commerce_api.configurations.security;

import com.shop.smart_commerce_api.entities.Permission;
import com.shop.smart_commerce_api.entities.Role;
import com.shop.smart_commerce_api.entities.RolePermission;
import com.shop.smart_commerce_api.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

@Component
public class CustomPermissionEvaluator {

    public boolean hasPermission(Authentication authentication, String permissionName) {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            return false;
        }

        User user = (User) principal;
        Role role = user.getRole();

        if (role == null) {
            return false;
        }

        Set<RolePermission> rolePermissions = role.getRolePermissions();
        for (RolePermission rolePermission : rolePermissions) {
            Permission permission = rolePermission.getPermission();
            if (permission != null && permission.getName().equals(permissionName)) {
                return true;
            }
        }

        return false;
    }
}

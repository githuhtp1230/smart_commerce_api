package com.shop.smart_commerce_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shop.smart_commerce_api.entities.RolePermission;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    boolean existsByRoleIdAndPermissionId(Integer roleId, Integer permissionId);
}

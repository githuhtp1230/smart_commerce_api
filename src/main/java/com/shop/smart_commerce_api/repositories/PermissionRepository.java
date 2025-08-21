package com.shop.smart_commerce_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.smart_commerce_api.entities.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Query(value = """
            SELECT p.*
            FROM permissions p
            JOIN role_permissions rp ON p.id = rp.permission_id
            WHERE rp.role_id = :roleId
            """, nativeQuery = true)
    List<Permission> findPermissionsByRoleId(@Param("roleId") Integer roleId);

    List<Permission> findByName(String name);

    List<Permission> findByValue(String value);
}
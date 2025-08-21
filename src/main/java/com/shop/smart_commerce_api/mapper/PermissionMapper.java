package com.shop.smart_commerce_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.shop.smart_commerce_api.dto.response.permission.PermissionResponse;
import com.shop.smart_commerce_api.entities.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toPermissionResponse(Permission permission);

    List<PermissionResponse> toPermissionResponses(List<Permission> permissions);

}
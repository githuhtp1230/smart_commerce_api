package com.shop.smart_commerce_api.dto.request.rolePremission;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPermissionToRoleRequest {
    private Integer roleId;
    private Integer permissionId;
}

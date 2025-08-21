package com.shop.smart_commerce_api.dto.response.permission;

import lombok.*;

@Data
@Builder
public class PermissionResponse {
    private Integer id;
    private String name;
    private String value;
}
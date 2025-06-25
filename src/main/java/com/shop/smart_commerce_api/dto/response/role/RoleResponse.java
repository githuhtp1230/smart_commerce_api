package com.shop.smart_commerce_api.dto.response.role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponse {
    private Integer id;
    private String name;
}

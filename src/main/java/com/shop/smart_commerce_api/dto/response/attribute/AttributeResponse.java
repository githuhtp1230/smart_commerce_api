package com.shop.smart_commerce_api.dto.response.attribute;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttributeResponse {
    private Integer id;
    private String name;
}

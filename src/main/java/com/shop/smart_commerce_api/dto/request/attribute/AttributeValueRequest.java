package com.shop.smart_commerce_api.dto.request.attribute;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeValueRequest {
    private Integer attributeId;
    private String value;
}

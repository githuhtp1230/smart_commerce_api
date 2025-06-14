package com.shop.smart_commerce_api.dto.response.attribute;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttributeValueResponse {
    private Integer id;
    private String value;
    private AttributeResponse attribute;
}

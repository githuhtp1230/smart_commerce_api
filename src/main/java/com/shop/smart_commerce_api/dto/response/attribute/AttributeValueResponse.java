package com.shop.smart_commerce_api.dto.response.attribute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttributeValueResponse {
    private Integer id;
    private String value;
    private String imageUrl;
    private AttributeResponse attribute;
}

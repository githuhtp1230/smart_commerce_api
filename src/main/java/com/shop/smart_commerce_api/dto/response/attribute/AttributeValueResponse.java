package com.shop.smart_commerce_api.dto.response.attribute;

import com.fasterxml.jackson.annotation.JsonInclude;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String imageUrl;
    private AttributeResponse attribute;
}

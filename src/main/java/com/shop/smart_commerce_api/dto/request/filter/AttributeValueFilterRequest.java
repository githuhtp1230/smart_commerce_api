package com.shop.smart_commerce_api.dto.request.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttributeValueFilterRequest {
    private Integer page = 0;
    private Integer size = 10;
    Boolean isDeleted;
}

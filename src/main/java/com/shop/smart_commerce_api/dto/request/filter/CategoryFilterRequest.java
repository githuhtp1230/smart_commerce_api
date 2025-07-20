package com.shop.smart_commerce_api.dto.request.filter;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class CategoryFilterRequest {
    @Builder.Default
    Boolean isDeleted = false;
}

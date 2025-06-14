package com.shop.smart_commerce_api.dto.request.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSummaryFilterRequest {
    Integer categoryId;
}

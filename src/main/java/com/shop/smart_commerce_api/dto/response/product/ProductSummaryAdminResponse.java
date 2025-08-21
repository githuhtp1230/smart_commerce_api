package com.shop.smart_commerce_api.dto.response.product;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSummaryAdminResponse extends ProductSummaryResponse {
    private Integer is_deleted;
}

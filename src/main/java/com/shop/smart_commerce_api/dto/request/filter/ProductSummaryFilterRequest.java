package com.shop.smart_commerce_api.dto.request.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummaryFilterRequest {
    Integer categoryId;
    private Boolean includeDeleted = true;
}

package com.shop.smart_commerce_api.dto.request.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequest {
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private Integer discountPercentage;
    private Boolean isActive;
}

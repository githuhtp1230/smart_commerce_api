package com.shop.smart_commerce_api.dto.response.promotion;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionResponse {
    private Integer id;
    private String description;
    private Instant startDate;
    private Instant endDate;
    private Integer discountValuePercent;
    private Boolean isActive;
}

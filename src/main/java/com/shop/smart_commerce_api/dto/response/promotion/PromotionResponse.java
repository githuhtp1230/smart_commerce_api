package com.shop.smart_commerce_api.dto.response.promotion;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PromotionResponse {
    private Integer id;
    private String description;
    private Integer discountValuePercent;
    private Instant startDate;
    private Instant endDate;
}

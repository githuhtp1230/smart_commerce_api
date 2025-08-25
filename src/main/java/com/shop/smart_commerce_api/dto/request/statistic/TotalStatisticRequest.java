package com.shop.smart_commerce_api.dto.request.statistic;

import com.shop.smart_commerce_api.constant.TotalStatisticCategory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalStatisticRequest {
    private TotalStatisticCategory category;
}

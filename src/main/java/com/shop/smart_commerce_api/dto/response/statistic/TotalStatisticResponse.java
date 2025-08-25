    package com.shop.smart_commerce_api.dto.response.statistic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalStatisticResponse {
    private Integer total;
    private Double percentVariation;
}

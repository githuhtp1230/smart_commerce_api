package com.shop.smart_commerce_api.dto.response.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductStatisticalResponse {
    private int totalSold;
    private double totalRevenue;
    private String time;
    private Integer productId;
}

package com.shop.smart_commerce_api.dto.response.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryStatisticalResponse {
    private int totalSold;
    private double totalRevenue;
    private String time;
    private Integer categoryId;
}

package com.shop.smart_commerce_api.dto.response.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticalResponse {
    private int totalProducts;
    private double totalAmount;
    private String time;
}

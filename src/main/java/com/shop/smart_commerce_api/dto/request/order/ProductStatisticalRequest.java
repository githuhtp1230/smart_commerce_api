package com.shop.smart_commerce_api.dto.request.order;

import lombok.Data;

@Data
public class ProductStatisticalRequest {
    private String time; // week, month, year, or null for all
    private Integer productId; // optional, if null then all products
}

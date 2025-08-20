package com.shop.smart_commerce_api.dto.response.review;

import java.time.Instant;

import com.shop.smart_commerce_api.dto.response.product.ProductResponse;
import com.shop.smart_commerce_api.dto.response.user.UserResponse;

import lombok.Data;

@Data
public class HistoryReviewResponse {
    private Integer id;
    private ProductResponse product;
    private UserResponse user;
    private Integer rating;
    private String comment;
    private Instant createdAt;
}

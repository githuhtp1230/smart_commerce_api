package com.shop.smart_commerce_api.dto.response.review;

import java.time.Instant;

import com.shop.smart_commerce_api.dto.response.user.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Integer id;
    private UserResponse user;
    private Integer productId;
    private String comment;
    private Integer rating;
    private Instant createdAt;
}

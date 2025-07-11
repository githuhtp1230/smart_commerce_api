package com.shop.smart_commerce_api.dto.request.review;

import lombok.Data;

@Data
public class ReviewRequest {
    private Integer productId;
    private Integer parentReviewId;
    private Integer rating;
    private String comment;
}

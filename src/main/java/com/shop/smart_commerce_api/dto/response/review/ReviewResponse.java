package com.shop.smart_commerce_api.dto.response.review;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class ReviewResponse {

    private Integer id;
    private Integer productId;
    private Integer userId;
    private Integer parentReviewId;
    private Integer rating;
    private String comment;
    private Instant createdAt;
    private List<ReviewResponse> replies;
    private Boolean isRepliesExisting;
    private String name;
    private String parentReviewName;
}

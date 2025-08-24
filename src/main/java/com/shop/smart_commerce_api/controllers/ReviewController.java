package com.shop.smart_commerce_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.review.ReviewRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.review.HistoryReviewResponse;
import com.shop.smart_commerce_api.dto.response.review.ProductReviewResponse;
import com.shop.smart_commerce_api.dto.response.review.ReviewResponse;
import com.shop.smart_commerce_api.repositories.ReviewRepository;
import com.shop.smart_commerce_api.services.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PutMapping("/{reviewId}")
    ApiResponse<ProductReviewResponse> updateReview(@PathVariable Integer reviewId,
            @RequestBody ReviewRequest request) {
        return ApiResponse.<ProductReviewResponse>builder()
                .code(200)
                .message("Update successfully")
                .data(reviewService.updateReView(reviewId, request))
                .build();
    }

    @GetMapping("/{reviewId}/replies")
    ApiResponse<List<ProductReviewResponse>> getListReviews(@PathVariable Integer reviewId) {
        return ApiResponse.<List<ProductReviewResponse>>builder()
                .code(200)
                .message("Get list reviews successfully")
                .data(reviewService.getListReviewReply(reviewId))
                .build();
    }

    @GetMapping("/users/{id}/reviews")
    public ApiResponse<List<HistoryReviewResponse>> getReviewHistory(@PathVariable("id") Integer userId) {
        return ApiResponse.<List<HistoryReviewResponse>>builder()
                .code(200)
                .message("Get review history successfully")
                .data(reviewService.getReviewHistoryByUser(userId))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ReviewResponse>> getAllRootReviews() {
        return ApiResponse.<List<ReviewResponse>>builder()
                .code(200)
                .message("Get all root reviews successfully")
                .data(reviewService.getAllRootReviews())
                .build();
    }
}

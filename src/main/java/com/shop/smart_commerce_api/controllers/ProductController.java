package com.shop.smart_commerce_api.controllers;

import java.util.List;

import com.cloudinary.Api;
import com.shop.smart_commerce_api.dto.request.filter.ProductSummaryFilterRequest;
import com.shop.smart_commerce_api.dto.request.review.ReviewRequest;
import com.shop.smart_commerce_api.dto.response.PageResponse;
import org.springframework.web.bind.annotation.*;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductDetailResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductSummaryResponse;
import com.shop.smart_commerce_api.dto.response.review.ReviewResponse;
import com.shop.smart_commerce_api.services.ProductService;
import com.shop.smart_commerce_api.services.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;

    @GetMapping
    public ApiResponse<List<ProductResponse>> getProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .code(200)
                .message("Get products successfully")
                .data(productService.getAllProducts())
                .build();
    }

    @GetMapping("/summaries")
    public ApiResponse<PageResponse<ProductSummaryResponse>> getProductSummaries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "${pagination.product_summary:15}") int limit,
            @ModelAttribute ProductSummaryFilterRequest filterRequest) {

        if (page <= 0) {
            page = 1;
        }
        page = page - 1;

        return ApiResponse.<PageResponse<ProductSummaryResponse>>builder()
                .code(200)
                .message("Get products successfully")
                .data(productService.getProductSummaries(filterRequest, page, limit))
                .build();
    }

    @GetMapping("/summaries/{productId}")
    public ApiResponse<ProductDetailResponse> getProductDetail(@PathVariable("productId") int productId) {
        return ApiResponse.<ProductDetailResponse>builder()
                .code(200)
                .message("Get product detail successfully")
                .data(productService.getProductDetail(productId))
                .build();
    }

    @PostMapping("/{productId}/reviews")
    ApiResponse<ReviewResponse> create(@PathVariable("productId") int productId, @RequestBody ReviewRequest request) {
        return ApiResponse.<ReviewResponse>builder()
                .code(200)
                .message("Create review successfully")
                .data(reviewService.create(request, productId))
                .build();
    }

    @GetMapping("/{productId}/reviews")
    ApiResponse<List<ReviewResponse>> getListReviews(@PathVariable Integer productId) {
        return ApiResponse.<List<ReviewResponse>>builder()
                .code(200)
                .message("Get list reviews successfully")
                .data(reviewService.getListReviewsByProductId(productId))
                .build();
    }

    @DeleteMapping("/{reviewId}")
    ApiResponse<?> delete(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
        return ApiResponse.builder()
                .code(200)
                .message("delete successfully")
                .build();
    }

}

package com.shop.smart_commerce_api.controllers;

import java.util.List;

import com.shop.smart_commerce_api.dto.request.filter.ProductSummaryFilterRequest;
import com.shop.smart_commerce_api.dto.response.PageResponse;
import org.springframework.web.bind.annotation.*;

import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductDetailResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductSummaryResponse;
import com.shop.smart_commerce_api.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
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

    @GetMapping("/{productId}")
    public ApiResponse<ProductDetailResponse> getProductDetail(@PathVariable("productId") int productId) {
        return ApiResponse.<ProductDetailResponse>builder()
                .code(200)
                .message("Get product detail successfully")
                .data(productService.getProductDetail(productId))
                .build();
    }

}

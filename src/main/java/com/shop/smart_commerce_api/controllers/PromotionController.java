package com.shop.smart_commerce_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.category.CreateCategoryRequest;
import com.shop.smart_commerce_api.dto.request.filter.CategoryFilterRequest;
import com.shop.smart_commerce_api.dto.request.filter.PromotionFilterRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.category.CategoryResponse;
import com.shop.smart_commerce_api.dto.response.promotion.PromotionResponse;
import com.shop.smart_commerce_api.services.CategoryService;
import com.shop.smart_commerce_api.services.PromotionService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    @GetMapping
    public ApiResponse<List<PromotionResponse>> getPromotions(@ModelAttribute PromotionFilterRequest request) {
        return ApiResponse.<List<PromotionResponse>>builder()
                .code(200)
                .message("Get promotions successfully")
                .data(promotionService.getPromotions(request))
                .build();
    }

    // @PostMapping
    // public ApiResponse<CategoryResponse> createPromotion(@RequestBody CreateCategoryRequest request) {
    //     return ApiResponse.<CategoryResponse>builder()
    //             .code(200)
    //             .message("Create category successfully")
    //             .data(promotionService.createPromotion(request))
    //             .build();
    // }

    // @PostMapping("/{categoryId}/delete")
    // public ApiResponse<?> deleteCategory(@PathVariable("categoryId") int categoryId) {
    //     categoryService.deleteCategory(categoryId);
    //     return ApiResponse.builder()
    //             .code(200)
    //             .message("Delete category successfully")
    //             .build();
    // }

}

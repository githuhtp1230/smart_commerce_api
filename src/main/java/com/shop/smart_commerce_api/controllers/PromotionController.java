package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.promotion.PromotionRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.promotion.PromotionResponse;
import com.shop.smart_commerce_api.services.PromotionService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/promotions")
public class PromotionController {
    public final PromotionService promotionService;

    @GetMapping
    public ApiResponse<List<PromotionResponse>> getAll(@RequestParam(required = false) Boolean isActive) {
        List<PromotionResponse> promotions;
        if (isActive != null) {
            promotions = promotionService.getAllByIsActive(isActive);
        } else {
            promotions = promotionService.getAll(); // Lấy tất cả nếu không có tham số
        }

        return ApiResponse.<List<PromotionResponse>>builder()
                .code(200)
                .message("Promotions retrieved successfully")
                .data(promotions)
                .build();
    }

    @PostMapping
    public ApiResponse<PromotionResponse> create(@RequestBody PromotionRequest request) {
        return ApiResponse.<PromotionResponse>builder()
                .code(200)
                .message("Promotion created successfully")
                .data(promotionService.create(request))
                .build();
    }

    @PostMapping("/{id}")
    ApiResponse<?> toggleIsActive(@PathVariable("id") int id) {
        return ApiResponse.builder()
                .code(200)
                .message("Promotion disabled successfully")
                .data(promotionService.toggleIsActive(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<PromotionResponse> update(@PathVariable("id") int id, @RequestBody PromotionRequest request) {
        return ApiResponse.<PromotionResponse>builder()
                .code(200)
                .message("Promotion updated successfully")
                .data(promotionService.update(id, request))
                .build();
    }

}

package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.filter.PromotionFilterRequest;
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
    public ApiResponse<List<PromotionResponse>> getAllPromotions(
            @RequestParam(required = false) Boolean isActived
    ) {
        PromotionFilterRequest filter = new PromotionFilterRequest();
        filter.setIsActived(isActived);

        return ApiResponse.<List<PromotionResponse>>builder()
                .code(200)
                .message("Get promotions successfully")
                .data(promotionService.getPromotions(filter))
                .build();
    }



    @PostMapping
    ApiResponse<String> createApiResponse(@RequestBody PromotionRequest request) {
        promotionService.create(request);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Promotion created successfully")
                .data("Promotion created")
                .build();
    }

    @DeleteMapping("/{id}/delete")
    ApiResponse<?> deleteApiResponse(@PathVariable("id") int id) {
        promotionService.delete(id);
        return ApiResponse.builder()
                .code(200)
                .message("Promotion deleted successfully")
                .build();
    }

    @PutMapping("/{id}/deactivate")
    ApiResponse<String> updateApiResponse(@PathVariable("id") int id, @RequestBody PromotionRequest request) {
        promotionService.update(id, request);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Promotion updated successfully")
                .data("Promotion updated")
                .build();
    }

}

package com.shop.smart_commerce_api.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.dto.request.product.ProductVariaRequest;
import com.shop.smart_commerce_api.dto.response.ApiResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import com.shop.smart_commerce_api.services.ProductVariationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-variations")
public class ProductVariationController {

    private final ProductVariationService productVariationService;

    @PostMapping
    ApiResponse<ProductVariationResponse> create(@RequestBody ProductVariaRequest request) {
        return ApiResponse.<ProductVariationResponse>builder()
                .data(productVariationService.create(request))
                .message("Product variation created successfully")
                .code(200)
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ProductVariationResponse> finddById(@PathVariable int id) {
        return ApiResponse.<ProductVariationResponse>builder()
                .code(200)
                .message("Product variation found successfully")
                .data(productVariationService.findById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<?> delete(@PathVariable int id) {
        productVariationService.delete(id);
        return ApiResponse.builder()
                .code(200)
                .message("Product variation deleted successfully")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ProductVariationResponse> update(@PathVariable int id, @RequestBody ProductVariaRequest request) {
        return ApiResponse.<ProductVariationResponse>builder()
                .data(productVariationService.update(id, request))
                .message("Product variation updated successfully")
                .code(200)
                .build();
    }
}

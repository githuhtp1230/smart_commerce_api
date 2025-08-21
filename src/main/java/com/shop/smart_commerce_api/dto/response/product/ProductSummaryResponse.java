package com.shop.smart_commerce_api.dto.response.product;

import java.time.Instant;

import com.shop.smart_commerce_api.dto.response.category.CategoryResponse;
import com.shop.smart_commerce_api.dto.response.promotion.PromotionResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProductSummaryResponse {
    private Integer id;
    private String name;
    private Double averageRating;
    private Long reviewCount;
    private Integer price;
    private Integer maxPrice;
    private String image;
    private PromotionResponse promotion;
    private CategoryResponse category;
    private Instant createdAt;

    public ProductSummaryResponse(Integer id, String name, String image, Double averageRating, Long reviewCount,
            Double price, Double maxPrice) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.price = price != null ? (int) Math.round(price) : null;
        this.maxPrice = maxPrice != null ? (int) Math.round(maxPrice) : null;

    }

}
package com.shop.smart_commerce_api.dto.response.product;

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
    private PromotionResponse promotion;

    public ProductSummaryResponse(Integer id, String name, Double averageRating, Long reviewCount, Double price) {
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.price = price != null ? (int) Math.round(price) : null;
    }

}
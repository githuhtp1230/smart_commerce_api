package com.shop.smart_commerce_api.dto.response.product;

import java.time.Instant;
import java.util.List;

import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.dto.response.promotion.PromotionResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponse {
    private Integer id;
    private String name;
    private String description;
    private Double averageRating;
    private Long reviewCount;
    private Instant createdAt;
    private Integer price;
    private PromotionResponse promotion;
    private List<String> images;
    private List<AttributeValueResponse> attributeValues;
    private List<ProductVariationResponse> variations;

    public ProductDetailResponse(Integer id, String name, String description, Double averageRating, Long reviewCount,
            Instant createdAt, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.createdAt = createdAt;
        this.price = price != null ? (int) Math.round(price) : null;
    }

}

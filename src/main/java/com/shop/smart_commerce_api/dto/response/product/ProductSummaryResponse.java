package com.shop.smart_commerce_api.dto.response.product;

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
    private Integer discountedPrice;

    public ProductSummaryResponse(Integer id, String name, Double averageRating, Long reviewCount, Double price,
            Double discountedPrice) {
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.price = price != null ? (int) Math.round(price) : null;
        this.discountedPrice = discountedPrice != null ? (int) Math.round(discountedPrice) : null;
    }

}
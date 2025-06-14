package com.shop.smart_commerce_api.dto.response.product;

import java.util.List;

import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
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
    private Double averageRating;
    private Long reviewCount;
    private List<AttributeValueResponse> attributesValues;
    private List<ProductVariationResponse> variations;

    public ProductDetailResponse(Integer id, String name, Double averageRating, Long reviewCount) {
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
    }

}

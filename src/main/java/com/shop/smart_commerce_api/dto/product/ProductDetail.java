package com.shop.smart_commerce_api.dto.product;

import java.util.List;

import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail {
    private Integer id;
    private String name;
    private Double averageRating;
    private Long reviewCount;
    private List<AttributeValueResponse> attributesValues;
    private List<ProductVariationResponse> variations;

    public ProductDetail(Integer id, String name, Double averageRating, Long reviewCount) {
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
    }

}

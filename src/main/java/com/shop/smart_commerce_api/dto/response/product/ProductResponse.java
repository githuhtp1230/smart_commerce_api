package com.shop.smart_commerce_api.dto.response.product;

import java.time.Instant;

import org.hibernate.annotations.ColumnDefault;

import com.shop.smart_commerce_api.dto.response.category.CategoryResponse;
import com.shop.smart_commerce_api.dto.response.promotion.PromotionResponse;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer stock;
    private Integer price;
    private CategoryResponse category;
    private PromotionResponse promotion;
    private Instant createdAt;
}

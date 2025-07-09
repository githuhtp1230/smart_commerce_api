package com.shop.smart_commerce_api.dto.response.cart;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import com.shop.smart_commerce_api.dto.response.promotion.PromotionResponse;
import com.shop.smart_commerce_api.entities.AttributeValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CartItemResponse {
    private Integer id;
    private ProductResponse product;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProductVariationResponse productVariation;
    private String image;
    private Integer quantity;

    public CartItemResponse(Integer id, Integer productId, Integer productVariationId, String image, Integer quantity) {
        this.id = id;
        this.product = ProductResponse.builder().id(productId).build();
        this.productVariation = productVariationId != null
                ? ProductVariationResponse.builder().id(productVariationId).build()
                : null;
        this.image = image;
        this.quantity = quantity;
    }
}

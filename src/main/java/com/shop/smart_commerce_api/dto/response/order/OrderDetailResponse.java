package com.shop.smart_commerce_api.dto.response.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shop.smart_commerce_api.dto.response.product.ProductResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import lombok.Data;

@Data
public class OrderDetailResponse {
    private Integer id;
    private ProductResponse product;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProductVariationResponse productVariation;
    private String image;
    private Integer quantity;
    private Integer price;

    public OrderDetailResponse(Integer id, Integer productId, Integer productVariationId, String image,
            Integer quantity, Integer price) {
        this.id = id;
        this.product = ProductResponse.builder().id(productId).build();
        this.productVariation = productVariationId != null
                ? ProductVariationResponse.builder().id(productVariationId).image(image).build()
                : null;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
    }
}

package com.shop.smart_commerce_api.dto.response.cart;

import com.shop.smart_commerce_api.dto.response.product.ProductResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCartItemQuantityResponse {
    private ProductResponse product;
    private Integer quantity;
}

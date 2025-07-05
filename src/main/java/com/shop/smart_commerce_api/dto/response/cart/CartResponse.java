package com.shop.smart_commerce_api.dto.response.cart;

import com.shop.smart_commerce_api.dto.response.product.ProductDetailResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartResponse {
    private ProductDetailResponse product;
}

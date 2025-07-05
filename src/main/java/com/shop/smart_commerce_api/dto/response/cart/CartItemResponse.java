package com.shop.smart_commerce_api.dto.response.cart;

import java.util.List;

import com.shop.smart_commerce_api.entities.AttributeValue;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponse {
    private Integer id;
    private Integer productId;
    private Integer quantity;
    private Integer price;
    private List<AttributeValue> attributesValues;
}

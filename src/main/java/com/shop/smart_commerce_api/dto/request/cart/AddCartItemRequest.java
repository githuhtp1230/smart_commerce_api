package com.shop.smart_commerce_api.dto.request.cart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCartItemRequest {
    private Integer productId;
    private Integer productVariationId;
    private Integer quantity;
}

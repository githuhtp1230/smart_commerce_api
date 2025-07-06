package com.shop.smart_commerce_api.dto.response.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageProductResponse {
    private Integer id;
    private Integer productId;
    private String imageUrl;
}

package com.shop.smart_commerce_api.dto.response.product;

import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductVariationResponse {
    private Integer id;
    private Integer price;
    private Integer discountedPrice;
    private Integer stock;
    private String image;
    private List<AttributeValueResponse> attributeResponses;
}

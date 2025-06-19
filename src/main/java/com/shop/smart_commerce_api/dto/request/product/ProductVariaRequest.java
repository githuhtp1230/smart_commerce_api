package com.shop.smart_commerce_api.dto.request.product;

import java.util.Set;

import com.shop.smart_commerce_api.dto.response.product.ProductResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import com.shop.smart_commerce_api.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariaRequest {

    private ProductResponse product;
    private Integer price;
    private Integer stock;
    private String image;
}

package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;

import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import com.shop.smart_commerce_api.entities.ProductVariation;

@Mapper(componentModel = "spring")
public interface ProductVariationMapper {
    ProductVariationResponse toProductVariationResponse(ProductVariation productVariation);
}

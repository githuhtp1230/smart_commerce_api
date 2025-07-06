package com.shop.smart_commerce_api.mapper;

import com.shop.smart_commerce_api.dto.response.product.ImageProductResponse;
import com.shop.smart_commerce_api.entities.ImageProduct;
import org.mapstruct.Mapper;

import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import com.shop.smart_commerce_api.entities.ProductVariation;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductVariationResponse toProductVariationResponse(ProductVariation productVariation);

    ImageProductResponse toImageProductResponse(ImageProduct imageProduct);
}

package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.shop.smart_commerce_api.dto.request.product.ProductVariaRequest;
import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import com.shop.smart_commerce_api.entities.Product;
import com.shop.smart_commerce_api.entities.ProductVariation;

@Mapper(componentModel = "spring")
public interface ProductVariationMapper {

    ProductVariation toProductVariation(ProductVariaRequest request);

    ProductVariationResponse toProductVariationResponse(ProductVariation productVariation);

    void updateProductVariationFromRequest(ProductVariaRequest request,
            @MappingTarget ProductVariation productVariation);
}

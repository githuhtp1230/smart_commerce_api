package com.shop.smart_commerce_api.mapper;

import com.shop.smart_commerce_api.dto.request.product.CreateProductRequest;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeResponse;
import com.shop.smart_commerce_api.dto.response.category.CategoryResponse;
import com.shop.smart_commerce_api.dto.response.product.ImageProductResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductSummaryResponse;
import com.shop.smart_commerce_api.entities.Attribute;
import com.shop.smart_commerce_api.entities.Category;
import com.shop.smart_commerce_api.entities.ImageProduct;
import com.shop.smart_commerce_api.entities.Product;

import java.util.List;

import org.mapstruct.Mapper;

import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import com.shop.smart_commerce_api.entities.ProductVariation;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductVariationResponse toProductVariationResponse(ProductVariation productVariation);

    ImageProductResponse toImageProductResponse(ImageProduct imageProduct);

    CategoryResponse toCategoryResponse(Category category);

    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toProductResponses(List<Product> list);

    Product toProduct(CreateProductRequest request);

    ProductSummaryResponse toProductSummaryResponse(Product product);
}

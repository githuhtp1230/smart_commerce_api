package com.shop.smart_commerce_api.services;

import java.util.List;

import com.shop.smart_commerce_api.dto.request.filter.ProductSummaryFilterRequest;
import com.shop.smart_commerce_api.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.response.product.ProductDetailResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductSummaryResponse;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import com.shop.smart_commerce_api.entities.Product;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.AttributeMapper;
import com.shop.smart_commerce_api.mapper.ProductVariationMapper;
import com.shop.smart_commerce_api.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
        private final ProductRepository productRepository;
        private final AttributeMapper attributeMapper;
        private final ProductVariationMapper productVariationMapper;

        public PageResponse<ProductSummaryResponse> getProductSummaries(ProductSummaryFilterRequest request,
                        int currentPage, int limit) {
                Pageable pageable = PageRequest.of(currentPage, limit);
                Page<ProductSummaryResponse> page = productRepository.findProductSummaries(request.getCategoryId(),
                                pageable);
                return PageResponse.<ProductSummaryResponse>builder()
                                .currentPage(page.getNumber() + 1)
                                .totalPages(page.getTotalPages())
                                .limit(page.getNumberOfElements())
                                .totalElements((int) page.getTotalElements())
                                .isLast(page.isLast())
                                .data(page.getContent())
                                .build();
        }

        public ProductDetailResponse getProductDetail(int productId) {
                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

                List<AttributeValueResponse> attributeValueResponses = product.getAttributeValueDetails().stream()
                                .map(attributeValueDetails -> {
                                        return attributeMapper
                                                        .toAttributeValueResponse(
                                                                        attributeValueDetails.getAttributeValue());
                                }).toList();

                List<ProductVariationResponse> productVariationResponses = product.getProductVariations().stream()
                                .map(productVariation -> {
                                        ProductVariationResponse productVariationResponse = productVariationMapper
                                                        .toProductVariationResponse(productVariation);

                                        List<AttributeValueResponse> variations = productVariation
                                                        .getProductVariationAttributes().stream()
                                                        .map(productVariationAttribute -> attributeMapper
                                                                        .toAttributeValueResponse(
                                                                                        productVariationAttribute
                                                                                                        .getAttributeValue()))
                                                        .toList();

                                        productVariationResponse.setAttributeResponses(variations);

                                        if (product.getPromotion() != null) {
                                                double discountPercent = product.getPromotion()
                                                                .getDiscountValuePercent();
                                                double discountedPrice = productVariation.getPrice()
                                                                * (1 - discountPercent / 100.0);

                                                productVariationResponse
                                                                .setDiscountedPrice((int) Math.round(discountedPrice));
                                        }

                                        return productVariationResponse;
                                }).toList();

                ProductDetailResponse productDetailResponse = productRepository.findProductDetailById(productId);
                productDetailResponse.setAttributesValues(attributeValueResponses);
                productDetailResponse.setVariations(productVariationResponses);
                return productDetailResponse;
        }

}

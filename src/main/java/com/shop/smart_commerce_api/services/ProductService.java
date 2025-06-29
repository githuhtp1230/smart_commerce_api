package com.shop.smart_commerce_api.services;

import java.util.List;
import java.util.Set;

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
import com.shop.smart_commerce_api.entities.ProductVariation;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.AttributeMapper;
import com.shop.smart_commerce_api.mapper.ProductVariationMapper;
import com.shop.smart_commerce_api.mapper.PromotionMapper;
import com.shop.smart_commerce_api.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
        private final ProductRepository productRepository;
        private final AttributeMapper attributeMapper;
        private final ProductVariationMapper productVariationMapper;
        private final PromotionMapper promotionMapper;
        private final AttributeService attributeService;

        public PageResponse<ProductSummaryResponse> getProductSummaries(ProductSummaryFilterRequest request,
                        int currentPage, int limit) {
                Pageable pageable = PageRequest.of(currentPage, limit);
                Page<ProductSummaryResponse> page = productRepository.findProductSummaries(request.getCategoryId(),
                                pageable);
                page.stream().forEach(productSummary -> {
                        Product product = productRepository.findById(productSummary.getId()).get();
                        productSummary.setPromotion(promotionMapper.toPromotionResponse(product.getPromotion()));
                });
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

                List<ProductVariationResponse> productVariationResponses = mapToProductVariationResponses(
                                product.getProductVariations());

                ProductDetailResponse productDetailResponse = productRepository.findProductDetailById(productId);
                productDetailResponse.setAttributeValues(
                                attributeService.maptoAttributeValueResponses(product.getAttributeValueDetails()));
                productDetailResponse.setVariations(productVariationResponses);
                productDetailResponse.setPromotion(promotionMapper.toPromotionResponse(product.getPromotion()));
                return productDetailResponse;
        }

        public List<ProductVariationResponse> mapToProductVariationResponses(Set<ProductVariation> productVariations) {
                return productVariations.stream()
                                .map(productVariation -> {
                                        ProductVariationResponse productVariationResponse = productVariationMapper
                                                        .toProductVariationResponse(productVariation);

                                        productVariationResponse.setAttributeValues(attributeService
                                                        .maptoAttributeValueResponsesFromPorudctVariationAttributes(
                                                                        productVariation.getProductVariationAttributes(),
                                                                        productVariation.getProduct().getId()));

                                        return productVariationResponse;
                                }).toList();
        }
}

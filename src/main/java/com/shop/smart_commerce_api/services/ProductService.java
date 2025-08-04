package com.shop.smart_commerce_api.services;

import java.util.List;
import java.util.Set;

import com.shop.smart_commerce_api.dto.request.filter.ProductSummaryFilterRequest;
import com.shop.smart_commerce_api.dto.response.PageResponse;
import com.shop.smart_commerce_api.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.response.product.ImageProductResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductDetailResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductSummaryResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import com.shop.smart_commerce_api.entities.ImageProduct;
import com.shop.smart_commerce_api.entities.Product;
import com.shop.smart_commerce_api.entities.ProductVariation;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.AttributeMapper;
import com.shop.smart_commerce_api.mapper.PromotionMapper;
import com.shop.smart_commerce_api.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
        private final ProductRepository productRepository;
        private final AttributeMapper attributeMapper;
        private final ProductMapper productMapper;
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
                        productSummary.setCategory(productMapper.toCategoryResponse(product.getCategory()));
                        productSummary.setCreatedAt(product.getCreatedAt());
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

        public void deleteProduct(int productId) {
                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
                product.setIsDeleted(1);
                productRepository.save(product);
        }

        public PageResponse<ProductSummaryResponse> getProductSummariesByStatus(boolean isDeleted, int currentPage,
                        int limit) {
                Pageable pageable = PageRequest.of(currentPage, limit);
                Page<ProductSummaryResponse> page = productRepository.findProductSummariesByDeletedStatus(isDeleted,
                                pageable);

                page.forEach(productSummary -> {
                        Product product = productRepository.findById(productSummary.getId()).orElse(null);
                        if (product != null) {
                                productSummary.setPromotion(
                                                promotionMapper.toPromotionResponse(product.getPromotion()));
                                productSummary.setCategory(productMapper.toCategoryResponse(product.getCategory()));
                                productSummary.setCreatedAt(product.getCreatedAt());
                        }
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
                                attributeService.mapToAttributeValueResponses(product.getAttributeValueDetails()));
                productDetailResponse.setVariations(productVariationResponses);
                productDetailResponse.setPromotion(promotionMapper.toPromotionResponse(product.getPromotion()));
                productDetailResponse.setImages(mapImageProductToStringImages(product.getImageProducts()));
                return productDetailResponse;
        }

        public List<ProductVariationResponse> mapToProductVariationResponses(Set<ProductVariation> productVariations) {
                return productVariations.stream()
                                .map(productVariation -> {
                                        ProductVariationResponse productVariationResponse = productMapper
                                                        .toProductVariationResponse(productVariation);

                                        productVariationResponse.setAttributeValues(attributeService
                                                        .mapToAttributeValueResponsesFromProductVariationAttributes(
                                                                        productVariation.getProductVariationAttributes(),
                                                                        productVariation.getProduct().getId()));

                                        return productVariationResponse;
                                }).toList();
        }

        public List<ProductResponse> getAllProducts() {
                return productMapper.toProductResponses(productRepository.findAll());
        }

        public List<String> mapImageProductToStringImages(Set<ImageProduct> imageProducts) {
                return imageProducts.stream().map(imageProduct -> imageProduct.getImageUrl()).toList();
        }
}

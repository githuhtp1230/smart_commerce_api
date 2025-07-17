package com.shop.smart_commerce_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductVariationResponse;
import com.shop.smart_commerce_api.entities.AttributeValue;
import com.shop.smart_commerce_api.entities.ProductVariation;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.ProductMapper;
import com.shop.smart_commerce_api.repositories.AttributeValueRepository;
import com.shop.smart_commerce_api.repositories.ProductVariationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductVariationService {
        private final AttributeValueRepository attributeValueRepository;
        private final AttributeService attributeService;
        private final ProductVariationRepository productVariationRepository;
        private final ProductMapper productMapper;

        public List<AttributeValueResponse> findAttributeValueByProductVariationIdAndMapToResponse(
                        Integer productVariationId) {
                List<AttributeValue> attributeValues = attributeValueRepository
                                .findAttributeValuesByProductVariationId(productVariationId);

                return attributeService
                                .mapToAttributeValueResponses(
                                                attributeValues);
        }

        public ProductVariationResponse mapToVariationResponse(Integer productVariationId) {
                ProductVariation productVariation = productVariationRepository.findById(productVariationId)
                                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIATION_NOT_FOUND));
                ProductVariationResponse productVariationResponse = productMapper
                                .toProductVariationResponse(productVariation);
                productVariationResponse.setAttributeValues(findAttributeValueByProductVariationIdAndMapToResponse(
                                productVariationId));
                return productVariationResponse;
        }
}

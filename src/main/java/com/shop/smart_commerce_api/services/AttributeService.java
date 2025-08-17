package com.shop.smart_commerce_api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.shop.smart_commerce_api.entities.AttributeValue;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.attribute.AttributeRequest;
import com.shop.smart_commerce_api.dto.request.attribute.AttributeUpdateRequest;
import com.shop.smart_commerce_api.dto.request.category.CreateCategoryRequest;
import com.shop.smart_commerce_api.dto.request.filter.AttributeFilterRequest;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeResponse;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.dto.response.category.CategoryResponse;
import com.shop.smart_commerce_api.mapper.AttributeMapper;
import com.shop.smart_commerce_api.entities.Attribute;
import com.shop.smart_commerce_api.entities.AttributeValueDetail;
import com.shop.smart_commerce_api.entities.Category;
import com.shop.smart_commerce_api.entities.ProductVariationAttribute;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.repositories.AttributeRepository;
import com.shop.smart_commerce_api.repositories.ProductAttributeValueImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttributeService {
    private final AttributeRepository attributeRepository;
    private final AttributeMapper attributeMapper;
    private final ProductAttributeValueImageRepository productAttributeValueImageRepository;

    public AttributeResponse createAttribute(AttributeRequest request) {
        Attribute existingAttribute = attributeRepository.findByNameAndIsDeletedIsFalse(request.getName());
        if (existingAttribute != null) {
            throw new AppException(ErrorCode.ATTRIBUTE_EXISTS);
        }
        Attribute newAttribute = attributeMapper.toAttribute(request);
        newAttribute.setIsDeleted(false);

        Attribute savedAttribute = attributeRepository.save(newAttribute);
        return attributeMapper.toAttributeResponse(savedAttribute);
    }

    public void delete(int id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_FOUND));
        attribute.setIsDeleted(true);
        attributeRepository.save(attribute);
    }

    public AttributeResponse toggleIsDeleted(int id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_FOUND));
        attribute.setIsDeleted(!attribute.getIsDeleted());
        attributeRepository.save(attribute);
        return attributeMapper.toAttributeResponse(attribute);
    }

    public AttributeResponse update(int id, AttributeUpdateRequest request) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_FOUND));
        attributeMapper.updateAttributeFromRequest(request, attribute);
        return attributeMapper.toAttributeResponse(attributeRepository.save(attribute));
    }

    public List<AttributeResponse> getAttributes(AttributeFilterRequest filter) {
        Boolean isDeleted = filter.getIsDeleted() != null ? filter.getIsDeleted() : false;

        List<Attribute> attributes = attributeRepository.findAttributes(isDeleted);

        return attributes.stream()
                .map(attribute -> AttributeResponse.builder()
                        .id(attribute.getId())
                        .name(attribute.getName())
                        .build())
                .toList();
    }

    public AttributeResponse getByName(String name) {
        Attribute attribute = attributeRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Attribute not found" + name));
        return attributeMapper.toAttributeResponse(attribute);
    }

    public AttributeResponse getById(int id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attribute not found" + id));
        return attributeMapper.toAttributeResponse(attribute);
    }

    public List<AttributeValueResponse> mapToAttributeValueResponses(Set<AttributeValueDetail> attributeValueDetails) {
        return attributeValueDetails.stream().map(attributeValueDetail -> {
            AttributeValueResponse attributeValueResponse = attributeMapper
                    .toAttributeValueResponse(attributeValueDetail.getAttributeValue());
            return attributeValueResponse;
        }).toList();
    }

    public List<AttributeValueResponse> mapToAttributeValueResponses(List<AttributeValue> attributeValues) {
        return attributeValues.stream().map(attributeMapper::toAttributeValueResponse).toList();
    }

    public List<AttributeValueResponse> mapToAttributeValueResponsesFromProductVariationAttributes(
            Set<ProductVariationAttribute> productVariationAttributes, int productId) {
        return productVariationAttributes.stream().map(productVariationAttribute -> {
            AttributeValueResponse attributeValueResponse = attributeMapper
                    .toAttributeValueResponse(productVariationAttribute.getAttributeValue());
            attributeValueResponse.setImageUrl(productAttributeValueImageRepository
                    .findImageUrlByAttributeValueIdAndProductId(attributeValueResponse.getId(), productId));
            return attributeValueResponse;
        }).toList();
    }

    public List<AttributeResponse> getAllAttribute() {
        var attributes = attributeRepository.findAll();
        return attributes.stream()
                .map(attributeMapper::toAttributeResponse).toList();
    }

    public List<AttributeResponse> getAllAttributeByIsDeleted(Boolean isDeleted) {
        var attributes = attributeRepository.findByIsDeleted(isDeleted);
        return attributes.stream()
                .map(attributeMapper::toAttributeResponse).toList();
    }
}

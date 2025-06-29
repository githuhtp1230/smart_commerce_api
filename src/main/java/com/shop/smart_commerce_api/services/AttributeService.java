package com.shop.smart_commerce_api.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.attribute.AttributeRequest;
import com.shop.smart_commerce_api.dto.request.attribute.AttributeUpdateRequest;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeResponse;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.mapper.AttributeMapper;
import com.shop.smart_commerce_api.entities.Attribute;
import com.shop.smart_commerce_api.entities.AttributeValueDetail;
import com.shop.smart_commerce_api.entities.ProductAttributeValueImage;
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

    public AttributeResponse create(AttributeRequest request) {
        Attribute attribute = attributeMapper.toAttribute(request);
        attributeRepository.save(attribute);
        return attributeMapper.toAttributeResponse(attribute);
    }

    public void delete(int id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_FOUND));
        attribute.setIsDeleted(true);
        attributeRepository.save(attribute);
    }

    public AttributeResponse update(int id, AttributeUpdateRequest request) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_FOUND));
        attributeMapper.updateAttributeFromRequest(request, attribute);
        return attributeMapper.toAttributeResponse(attributeRepository.save(attribute));
    }

    public List<AttributeResponse> getAll() {
        var attributes = attributeRepository.findAll();
        return attributes.stream()
                .map(attributeMapper::toAttributeResponse)
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

    public List<AttributeValueResponse> maptoAttributeValueResponses(Set<AttributeValueDetail> attributeValueDetails) {
        return attributeValueDetails.stream().map(attributeValueDetail -> {
            AttributeValueResponse attributeValueResponse = attributeMapper
                    .toAttributeValueResponse(attributeValueDetail.getAttributeValue());
            return attributeValueResponse;
        }).toList();
    }

    public List<AttributeValueResponse> maptoAttributeValueResponsesFromPorudctVariationAttributes(
            Set<ProductVariationAttribute> productVariationAttributes, int productId) {
        return productVariationAttributes.stream().map(productVariationAttribute -> {
            AttributeValueResponse attributeValueResponse = attributeMapper
                    .toAttributeValueResponse(productVariationAttribute.getAttributeValue());
            attributeValueResponse.setImageUrl(productAttributeValueImageRepository
                    .findImageUrlByAttributeValueIdAndProductId(attributeValueResponse.getId(), productId));
            return attributeValueResponse;
        }).toList();
    }
}

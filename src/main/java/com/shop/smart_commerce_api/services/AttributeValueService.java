package com.shop.smart_commerce_api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.attribute.AttributeValueRequest;
import com.shop.smart_commerce_api.dto.request.attribute.AttributeValueUpdateRequest;
import com.shop.smart_commerce_api.dto.request.filter.AttributeValueFilterRequest;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeResponse;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.entities.Attribute;
import com.shop.smart_commerce_api.entities.AttributeValue;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.AttributeMapper;
import com.shop.smart_commerce_api.mapper.AttributeValueMapper;
import com.shop.smart_commerce_api.repositories.AttributeRepository;
import com.shop.smart_commerce_api.repositories.AttributeValueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttributeValueService {
    private final AttributeValueRepository attributeValueRepository;
    private final AttributeRepository attributeRepository;
    private final AttributeValueMapper attributeValueMapper;
    private final AttributeMapper attributeMapper;

    public List<AttributeValueResponse> getAttributeValues(AttributeValueFilterRequest filter) {
        Boolean isDeleted = filter.getIsDeleted() != null ? filter.getIsDeleted() : false;
        Integer attributeId = filter.getAttributeId();
        List<AttributeValue> attributesValues = attributeValueRepository.findAttributesValues(isDeleted, attributeId);

        return attributesValues.stream()
                .map(attributeValue -> {
                    AttributeResponse attributeResponse = null;
                    if (attributeValue.getAttribute() != null) {
                        attributeResponse = attributeMapper.toAttributeResponse(attributeValue.getAttribute());
                    }

                    return AttributeValueResponse.builder()
                            .id(attributeValue.getId())
                            .value(attributeValue.getValue())
                            .attribute(attributeResponse)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public AttributeValueResponse createAttributeValue(AttributeValueRequest request) {
        // Kiểm tra trùng value
        AttributeValue existing = attributeValueRepository.findByValueAndIsDeletedIsFalse(request.getValue());
        if (existing != null) {
            throw new AppException(ErrorCode.ATTRIBUTE_VALUE_EXISTS);
        }

        // Kiểm tra attributeId hợp lệ
        Attribute attribute = attributeRepository.findById(request.getAttributeId())
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_FOUND));

        // Mapping request thành entity
        AttributeValue newAttributeValue = attributeValueMapper.toAttributeValue(request);
        newAttributeValue.setAttribute(attribute); // Gán attribute vào
        newAttributeValue.setIsDeleted(false);

        AttributeValue saved = attributeValueRepository.save(newAttributeValue);
        return attributeValueMapper.toAttributeValueResponse(saved);
    }

    public void deleteAttributeValue(Integer attributeValueId) {
        AttributeValue attributeValue = attributeValueRepository
                .findById(attributeValueId)
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_VALUE_NOT_FOUND));
        attributeValue.setIsDeleted(true);
        attributeValueRepository.save(attributeValue);
    }

    public AttributeValueResponse updateAtrributeValue(Integer attributeValueId, AttributeValueUpdateRequest request) {
        AttributeValue attributeValue = attributeValueRepository
                .findById(attributeValueId)
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_VALUE_NOT_FOUND));
        attributeValue.setValue(request.getValue());
        return attributeValueMapper.toAttributeValueResponse(attributeValueRepository.save(attributeValue));
    }

    public List<AttributeValueResponse> getAttributeValuesByAttributeId(Integer attributeId) {
        return attributeValueRepository.findByAttributeId(attributeId)
                .stream()
                .map(attributeValueMapper::toAttributeValueResponse)
                .toList();
    }
}

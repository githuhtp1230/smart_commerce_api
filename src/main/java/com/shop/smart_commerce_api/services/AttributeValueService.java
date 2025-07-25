package com.shop.smart_commerce_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.attribute.AttributeValueRequest;
import com.shop.smart_commerce_api.dto.request.attribute.AttributeValueUpdateRequest;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.entities.Attribute;
import com.shop.smart_commerce_api.entities.AttributeValue;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.AttributeValueMapper;
import com.shop.smart_commerce_api.repositories.AttributeRepository;
import com.shop.smart_commerce_api.repositories.AttributeValueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttributeValueService {
    private final AttributeValueRepository attributeValueRepository;
    private final AttributeValueMapper attributeValueMapper;
    private final AttributeRepository attributeRepository;;

    public List<AttributeValueResponse> getAllAttributeValues() {
        return attributeValueRepository.findAll()
                .stream()
                .map(attributeValueMapper::toAttributeValueResponse)
                .toList();
    }

    public AttributeValueResponse createAttributeValue(AttributeValueRequest request) {
        Attribute attribute = attributeRepository.findById(request.getAttributeId())
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_FOUND));
        AttributeValue attributeValue = attributeValueMapper.toAttributeValue(request);
        attributeValue.setAttribute(attribute);
        return attributeValueMapper.toAttributeValueResponse(attributeValueRepository.save(attributeValue));
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

package com.shop.smart_commerce_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.attribute.AttributeRequest;
import com.shop.smart_commerce_api.dto.attribute.AttributeUpdateRequest;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeResponse;
import com.shop.smart_commerce_api.mapper.AttributeMapper;
import com.shop.smart_commerce_api.entities.Attribute;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.repositories.AttributeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttributeService {
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private AttributeMapper attributeMapper;

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
        System.out.println(attribute);
        attributeMapper.updateAttributeFromRequest(request, attribute);
        System.out.println(attribute);
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
}

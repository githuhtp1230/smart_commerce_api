package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.shop.smart_commerce_api.dto.attribute.AttributeRequest;
import com.shop.smart_commerce_api.dto.attribute.AttributeUpdateRequest;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeResponse;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.entities.AttributeValue;
import com.shop.smart_commerce_api.entities.Attribute;

@Mapper(componentModel = "spring")
public interface AttributeMapper {
    AttributeValueResponse toAttributeValueResponse(AttributeValue attributeValue);

    Attribute toAttribute(AttributeRequest request);

    AttributeResponse toAttributeResponse(Attribute attribute);

    void updateAttributeFromRequest(AttributeUpdateRequest request, @MappingTarget Attribute attribute);
}

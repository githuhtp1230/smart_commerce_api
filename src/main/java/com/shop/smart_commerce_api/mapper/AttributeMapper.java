package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;

import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.entities.AttributeValue;

@Mapper(componentModel = "spring")
public interface AttributeMapper {
    AttributeValueResponse toAttributeValueResponse(AttributeValue attributeValue);
}

package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;
import com.shop.smart_commerce_api.dto.request.attribute.AttributeValueRequest;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeValueResponse;
import com.shop.smart_commerce_api.entities.AttributeValue;

@Mapper(componentModel = "spring", uses = AttributeMapper.class)
public interface AttributeValueMapper {
    AttributeValue toAttributeValue(AttributeValueRequest request);

    AttributeValueResponse toAttributeValueResponse(AttributeValue attributeValue);
}

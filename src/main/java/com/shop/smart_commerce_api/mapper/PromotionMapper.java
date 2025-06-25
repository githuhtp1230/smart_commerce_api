package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;

import com.shop.smart_commerce_api.dto.response.promotion.PromotionResponse;
import com.shop.smart_commerce_api.entities.Promotion;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    PromotionResponse toPromotionResponse(Promotion promotion);
}

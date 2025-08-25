package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.shop.smart_commerce_api.dto.request.promotion.CreatePromotionRequest;
import com.shop.smart_commerce_api.dto.response.promotion.PromotionResponse;
import com.shop.smart_commerce_api.entities.Promotion;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    PromotionResponse toPromotionResponse(Promotion promotion);

    @Mapping(target = "description", source = "description")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "discountValuePercent", source = "discountValuePercent")
    @Mapping(target = "isActive", source = "isActive")
    @Mapping(target = "isShowAtHome", expression = "java(false)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "products", ignore = true)
    Promotion toPromotion(CreatePromotionRequest request);
}

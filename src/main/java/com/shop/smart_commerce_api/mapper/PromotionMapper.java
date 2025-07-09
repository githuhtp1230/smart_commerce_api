package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.shop.smart_commerce_api.dto.request.promotion.PromotionRequest;
import com.shop.smart_commerce_api.dto.response.promotion.PromotionResponse;
import com.shop.smart_commerce_api.entities.Promotion;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    PromotionResponse toPromotionResponse(Promotion promotion);

    Promotion toPromotion(PromotionRequest request);

    void updatePromotionFromRequest(PromotionRequest request, @MappingTarget Promotion promotion);
}

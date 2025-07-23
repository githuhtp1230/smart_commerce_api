package com.shop.smart_commerce_api.services;

import java.util.List;

import org.springframework.stereotype.Service;


import com.shop.smart_commerce_api.dto.request.filter.PromotionFilterRequest;
import com.shop.smart_commerce_api.dto.response.promotion.PromotionResponse;
import com.shop.smart_commerce_api.entities.Promotion;
import com.shop.smart_commerce_api.mapper.PromotionMapper;
import com.shop.smart_commerce_api.repositories.PromotionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    public List<PromotionResponse> getPromotions(PromotionFilterRequest request) {
        Boolean isActived = request.getIsActived();
        if (isActived == null) {
            isActived = false;
        }
        List<Promotion> promotions = promotionRepository.findPromotion(isActived);
        return promotions.stream()
                .map(promotion -> {
                    return PromotionResponse.builder()
                            .id(promotion.getId())
                            .description(promotion.getDescription())
                            .discountValuePercent(promotion.getDiscountValuePercent())
                            .startDate(promotion.getStartDate())
                            .endDate(promotion.getEndDate())
                            .build();
                })
                .toList();
    }

   
}

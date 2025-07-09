package com.shop.smart_commerce_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.promotion.PromotionRequest;
import com.shop.smart_commerce_api.dto.response.attribute.AttributeResponse;
import com.shop.smart_commerce_api.dto.response.promotion.PromotionResponse;
import com.shop.smart_commerce_api.entities.Promotion;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.PromotionMapper;
import com.shop.smart_commerce_api.repositories.PromotionRepository;

import lombok.RequiredArgsConstructor;
import lombok.var;

@Service
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    public List<PromotionResponse> getAll() {
        var promotions = promotionRepository.findAll();
        return promotions.stream()
                .map(promotionMapper::toPromotionResponse)
                .toList();
    }

    public void create(PromotionRequest request) {
        Promotion promotion = promotionMapper.toPromotion(request);
        promotionRepository.save(promotion);
    }

    public void delete(int id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        promotionRepository.delete(promotion);
    }

    public void update(int id, PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        promotionMapper.updatePromotionFromRequest(request, promotion);
        promotionRepository.save(promotion);
    }

}

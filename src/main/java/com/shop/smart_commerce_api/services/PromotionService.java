
package com.shop.smart_commerce_api.services;

import java.time.Instant;

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

@Service
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    public void promotionCheck() {
        List<Promotion> promotions = promotionRepository.findAll();
        Instant now = Instant.now();
        boolean changed = false;
        for (Promotion promotion : promotions) {
            if (promotion.getEndDate() != null && promotion.getEndDate().isBefore(now)
                    && Boolean.TRUE.equals(promotion.getIsActive())) {
                promotion.setIsActive(false);
                changed = true;
            } else if (promotion.getStartDate() != null && promotion.getStartDate().isBefore(now)
                    && promotion.getEndDate() != null && promotion.getEndDate().isAfter(now)
                    && Boolean.FALSE.equals(promotion.getIsActive())) {
                promotion.setIsActive(true);
                changed = true;
            }
        }
        if (changed)
            promotionRepository.saveAll(promotions);
    }

    public List<PromotionResponse> getAll() {
        List<Promotion> promotions = promotionRepository.findAll();
        promotionCheck();
        return promotions.stream()
                .map(promotionMapper::toPromotionResponse)
                .toList();
    }

    public List<PromotionResponse> getAllByIsActive(Boolean isActive) {
        List<Promotion> promotions = promotionRepository.findByIsActive(isActive);
        promotionCheck();
        return promotions.stream()
                .map(promotionMapper::toPromotionResponse)
                .toList();
    }

    public PromotionResponse create(PromotionRequest request) {
        Promotion promotion = promotionMapper.toPromotion(request);
        promotion.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        promotionRepository.save(promotion);
        return promotionMapper.toPromotionResponse(promotion);
    }

    public PromotionResponse toggleIsActive(Integer id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));

        boolean currentStatus = Boolean.TRUE.equals(promotion.getIsActive());
        promotion.setIsActive(!currentStatus);

        promotionRepository.save(promotion);
        return promotionMapper.toPromotionResponse(promotion);
    }

    public PromotionResponse update(int id, PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        promotionMapper.updatePromotionFromRequest(request, promotion);
        promotionRepository.save(promotion);
        return promotionMapper.toPromotionResponse(promotion);
    }

}

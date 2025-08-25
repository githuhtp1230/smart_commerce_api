
package com.shop.smart_commerce_api.services;

import java.time.Instant;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.promotion.CreatePromotionRequest;
import com.shop.smart_commerce_api.dto.request.promotion.UpdatePromotionRequest;
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

    @Scheduled(fixedRate = 60 * 1000)
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
        System.out.println("Promotion status updated at " + now);
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

    public PromotionResponse create(CreatePromotionRequest request) {
        Promotion promotion = promotionMapper.toPromotion(request);
        promotion.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        // ensure default for isShowAtHome when creating
        promotion.setIsShowAtHome(Boolean.FALSE);
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

    public PromotionResponse update(int id, UpdatePromotionRequest request) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));

        if (Boolean.TRUE.equals(request.getIsShowAtHome())
                && promotionRepository.existsByIsShowAtHomeTrueAndIdNot(promotion.getId())) {
            throw new AppException(ErrorCode.PROMOTION_HOME_ALREADY_SET);
        }
        if (request.getDescription() != null)
            promotion.setDescription(request.getDescription());
        if (request.getStartDate() != null)
            promotion.setStartDate(request.getStartDate());
        if (request.getEndDate() != null)
            promotion.setEndDate(request.getEndDate());
        if (request.getDiscountValuePercent() != null)
            promotion.setDiscountValuePercent(request.getDiscountValuePercent());
        if (request.getIsShowAtHome() != null)
            promotion.setIsShowAtHome(request.getIsShowAtHome());
        if (request.getIsActive() != null)
            promotion.setIsActive(request.getIsActive());

        promotionRepository.save(promotion);
        return promotionMapper.toPromotionResponse(promotion);
    }

}

package com.shop.smart_commerce_api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.filter.PromotionFilterRequest;
import com.shop.smart_commerce_api.dto.request.promotion.PromotionRequest;
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

    public List<PromotionResponse> getPromotions(PromotionFilterRequest filter) {
        Boolean isActive = filter.getIsActived() != null ? filter.getIsActived() : true;
        List<Promotion> promotions = promotionRepository.findByIsActive(isActive);

        return promotions.stream()
                .map(promotion -> PromotionResponse.builder()
                        .id(promotion.getId())
                        .description(promotion.getDescription())
                        .discountValuePercent(promotion.getDiscountValuePercent())
                        .startDate(promotion.getStartDate())
                        .endDate(promotion.getEndDate())
                        .build())
                .collect(Collectors.toList());
    }

    public void create(PromotionRequest request) {
        Promotion promotion = promotionMapper.toPromotion(request);
        promotion.setIsActive(true); // mặc định isActive = true khi tạo mới
        promotionRepository.save(promotion);
    }

    public void update(int id, PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));
        promotionMapper.updatePromotionFromRequest(request, promotion);
        promotionRepository.save(promotion);
    }

    /**
     * Chuyển trạng thái khuyến mãi sang không hoạt động thay vì xóa hẳn
     */
    public void delete(int id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));

        promotion.setIsActive(false); // chỉ set trạng thái thành không hoạt động
        promotionRepository.save(promotion);
    }
}

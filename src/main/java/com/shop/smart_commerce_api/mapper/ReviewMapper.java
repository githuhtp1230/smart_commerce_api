package com.shop.smart_commerce_api.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.shop.smart_commerce_api.dto.request.review.ReviewRequest;
import com.shop.smart_commerce_api.dto.response.review.HistoryReviewResponse;
import com.shop.smart_commerce_api.dto.response.review.ProductReviewResponse;
import com.shop.smart_commerce_api.dto.response.review.ReviewResponse;
import com.shop.smart_commerce_api.entities.Review;
import com.shop.smart_commerce_api.entities.Role;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toReview(ReviewRequest request);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "parentReview.id", target = "parentReviewId")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "parentReview.user.name", target = "parentReviewName")
    @Mapping(source = "user.avatar", target = "avatar")
    ProductReviewResponse toResponse(Review review);

    List<ProductReviewResponse> toResponseList(List<Review> reviews);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "parentReview", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    void updateEntityFromRequest(ReviewRequest request, @MappingTarget Review review);

    @Mapping(target = "product.category.children", ignore = true)
    default String map(Role role) {
        return role != null ? role.getName() : null;
    }

    HistoryReviewResponse toHistoryReviewResponse(Review review);

    ReviewResponse toReviewResponse(Review review);
}

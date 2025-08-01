package com.shop.smart_commerce_api.services;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.review.ReviewRequest;
import com.shop.smart_commerce_api.dto.response.review.ReviewResponse;
import com.shop.smart_commerce_api.entities.Product;
import com.shop.smart_commerce_api.entities.Review;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.mapper.ReviewMapper;
import com.shop.smart_commerce_api.repositories.ProductRepository;
import com.shop.smart_commerce_api.repositories.ReviewRepository;
import com.shop.smart_commerce_api.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    public ReviewResponse create(ReviewRequest request, Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy id sản phẩm"));
        // Lấy user từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        Review parentReview = null;
        if (request.getParentReviewId() != null) {
            parentReview = reviewRepository.findById(request.getParentReviewId())
                    .orElseThrow(() -> new RuntimeException("Không tồn tại bài đăng nào"));
        }
        Review review = reviewMapper.toReview(request);
        review.setProduct(product);
        review.setUser(user);
        review.setParentReview(parentReview);
        review.setCreatedAt(Instant.now());

        reviewRepository.save(review);

        return reviewMapper.toResponse(review);
    }

    public List<ReviewResponse> getListReviewsByProductId(Integer productId) {
        // Lấy tất cả comment gốc (parentReviewId = null)
        List<Review> rootReviews = reviewRepository.findByProductIdAndParentReviewIsNullOrderByCreatedAtDesc(productId);
        List<ReviewResponse> responses = rootReviews.stream().map(review -> {
            boolean isRepliesExisting = review.getReviews().size() > 0;
            ReviewResponse response = reviewMapper.toResponse(review);
            response.setIsRepliesExisting(isRepliesExisting);
            return response;
        }).toList();
        return responses;
    }

    public List<ReviewResponse> getListReviewReply(Integer reviewId) {
        List<Review> reviewReplies = reviewRepository.findByParentReviewId(reviewId);

        return reviewReplies.stream().map(reply -> {
            ReviewResponse response = reviewMapper.toResponse(reply);
            boolean hasSubReplies = reply.getReviews().size() > 0;
            response.setIsRepliesExisting(hasSubReplies);
            return response;
        }).toList();
    }

    public List<ReviewResponse> mapToReviewResponseList(Set<Review> reviews) {
        return reviews.stream().map(reviewMapper::toResponse).toList();
    }

    public ReviewResponse updateReView(Integer reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Không tồn tại reviewId"));
        reviewMapper.updateEntityFromRequest(request, review);
        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    public void deleteReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Xóa không thành công"));
        reviewRepository.delete(review);
    }
    // private ReviewResponse mapReviewToResponseWithReplies(Review review) {
    // ReviewResponse reviewResponse = reviewMapper.toResponse(review);
    // if (review.getReviews() != null && !review.getReviews().isEmpty()) {
    // reviewResponse.setReplies(review.getReviews().stream()
    // .map(this::mapReviewToResponseWithReplies) // gọi đệ quy
    // .toList());
    // } else {
    // reviewResponse.setReplies(null);
    // }
    // return reviewResponse;
    // }
}

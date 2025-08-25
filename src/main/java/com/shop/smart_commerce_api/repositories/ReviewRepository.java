package com.shop.smart_commerce_api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.smart_commerce_api.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByProductIdAndParentReviewIsNullOrderByCreatedAtDesc(Integer productId);

    List<Review> findByParentReviewId(Integer parentReviewId);

    List<Review> findByUserIdOrderByCreatedAtDesc(Integer userId);

    Page<Review> findByParentReviewIsNullOrderByCreatedAtDesc(Pageable pageable);

}

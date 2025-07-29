package com.shop.smart_commerce_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.smart_commerce_api.entities.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    List<Promotion> findByIsActive(Boolean isActive);
}

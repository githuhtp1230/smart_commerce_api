package com.shop.smart_commerce_api.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.shop.smart_commerce_api.entities.Promotion;
import org.springframework.data.repository.query.Param;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    Promotion findByDescriptionAndIsActive(String description, Boolean isActive);

    @Query("SELECT c FROM Promotion c WHERE (:isActive IS NULL OR c.isActive = :isActive)")
    List<Promotion> findPromotion(@Param("isActive") Boolean isActive);
}
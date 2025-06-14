package com.shop.smart_commerce_api.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shop.smart_commerce_api.entities.ProductVariation;

@Repository
public interface ProductVariationRepository extends JpaRepository<ProductVariation, Integer> {

}

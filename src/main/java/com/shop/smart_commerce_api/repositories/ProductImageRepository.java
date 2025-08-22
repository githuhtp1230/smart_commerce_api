package com.shop.smart_commerce_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.smart_commerce_api.entities.ImageProduct;

public interface ProductImageRepository extends JpaRepository<ImageProduct, Integer> {

}

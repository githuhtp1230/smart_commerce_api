package com.shop.smart_commerce_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.smart_commerce_api.entities.ProductAttributeValueImage;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface ProductAttributeValueImageRepository extends JpaRepository<ProductAttributeValueImage, Integer> {
        ProductAttributeValueImage findProductAttributeValueImageByAttributeValueIdAndProductId(int attributeValueId,
                        int productId);

        @Query("SELECT pavi.imageUrl FROM ProductAttributeValueImage pavi WHERE pavi.product.id = :productId AND pavi.attributeValue.id = :attributeValueId")
        String findImageUrlByAttributeValueIdAndProductId(
                        @Param("attributeValueId") int attributeValueId, @Param("productId") int productId);

}

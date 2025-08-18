package com.shop.smart_commerce_api.repositories;

import com.shop.smart_commerce_api.entities.Attribute;
import com.shop.smart_commerce_api.entities.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Integer> {
        AttributeValue findByValueAndIsDeletedIsFalse(String value);

        @Query("""
                        SELECT pva.attributeValue
                        FROM ProductVariationAttribute pva
                        WHERE pva.productVariation.id = :productVariationId
                        """)
        List<AttributeValue> findAttributeValuesByProductVariationId(
                        @Param("productVariationId") Integer productVariationId);

        @Query("""
                        SELECT pva.attributeValue
                        FROM ProductVariationAttribute pva
                        WHERE pva.productVariation.product.id = :productId
                        """)
        List<AttributeValue> findAttributeValuesByProductId(@Param("productId") Integer productId);

        List<AttributeValue> findByAttributeId(Integer attributeId);

        @Query("""
                        SELECT a FROM AttributeValue a
                        JOIN FETCH a.attribute
                        WHERE (:isDeleted IS NULL OR a.isDeleted = :isDeleted)
                        AND (:attributeId IS NULL OR a.attribute.id = :attributeId)
                        """)
        List<AttributeValue> findAttributesValues(@Param("isDeleted") Boolean isDeleted,
                        @Param("attributeId") Integer attributeId);

}

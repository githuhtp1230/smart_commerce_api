package com.shop.smart_commerce_api.repositories;

import com.shop.smart_commerce_api.dto.response.product.ProductSummaryResponse;
import com.shop.smart_commerce_api.dto.response.product.ProductDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.smart_commerce_api.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    @Query("""
                SELECT new com.shop.smart_commerce_api.dto.response.product.ProductSummaryResponse (
                    p.id,
                    p.name,
                    CAST(COALESCE(AVG(r.rating * 1.0), 0.0) AS double),
                    COUNT(DISTINCT r.id),
                    CAST(MIN(pr.price) AS double)
                )
                FROM Product p
                LEFT JOIN p.reviews r
                LEFT JOIN p.promotion po
                LEFT JOIN p.productVariations pr
                WHERE (:categoryId IS NULL OR (p.category IS NOT NULL AND p.category.id = :categoryId))
                GROUP BY p.id, p.name
            """)
    Page<ProductSummaryResponse> findProductSummaries(@Param("categoryId") Integer categoryId, Pageable pageable);

    @Query("""
                SELECT new com.shop.smart_commerce_api.dto.response.product.ProductDetailResponse (
                    p.id,
                    p.name,
                    CAST(COALESCE(AVG(r.rating * 1.0), 0.0) AS double),
                    COUNT(DISTINCT r.id),
                    p.createdAt,
                    CAST(MIN(pr.price) AS double)
                )
                FROM Product p
                LEFT JOIN p.reviews r
                LEFT JOIN p.promotion po
                LEFT JOIN p.productVariations pr
                WHERE p.id = :productId
                GROUP BY p.id, p.name
            """)
    ProductDetailResponse findProductDetailById(@Param("productId") Integer productId);
}
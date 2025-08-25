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

import java.time.Instant;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    @Query(value = """
                SELECT
                    p.id,
                    p.name,
                    ip.image_url AS image,
                    CAST(COALESCE(AVG(r.rating * 1.0), 0.0) AS DOUBLE) AS average_rating,
                    COUNT(DISTINCT r.id) AS review_count,
                    CAST(COALESCE(MIN(pr.price), p.price) AS double),
                    CAST(COALESCE(MAX(pr.price), p.price) AS double)
                FROM products p
                LEFT JOIN (
                    SELECT product_id, image_url,
                           ROW_NUMBER() OVER (PARTITION BY product_id ORDER BY id) AS rn
                    FROM image_products
                ) ip ON ip.product_id = p.id AND ip.rn = 1
                LEFT JOIN reviews r ON r.product_id = p.id
                LEFT JOIN promotions po ON po.id = p.promotion_id
                LEFT JOIN product_variations pr ON pr.product_id = p.id
                WHERE p.is_deleted = 0
                  AND (:categoryId IS NULL OR (p.category_id IS NOT NULL AND p.category_id = :categoryId))
                  AND (:query IS NULL OR p.name LIKE CONCAT('%', :query, '%'))
                GROUP BY p.id, p.name, ip.image_url
            """, countQuery = """
                SELECT COUNT(DISTINCT p.id)
                FROM products p
                WHERE p.is_deleted = 0
                  AND (:categoryId IS NULL OR (p.category_id IS NOT NULL AND p.category_id = :categoryId))
                  AND (:query IS NULL OR p.name LIKE CONCAT('%', :query, '%'))
            """, nativeQuery = true)
    Page<ProductSummaryResponse> findProductSummaries(@Param("categoryId") Integer categoryId,
            @Param("query") String query, @Param("min") Integer min, @Param("max") Integer max,
            Pageable pageable);

    @Query("""
                SELECT new com.shop.smart_commerce_api.dto.response.product.ProductDetailResponse (
                    p.id,
                    p.name,
                    CAST(COALESCE(AVG(r.rating * 1.0), 0.0) AS double),
                    COUNT(DISTINCT r.id),
                    p.createdAt,
                    CAST(COALESCE(MIN(pr.price), p.price) AS double)
                )
                FROM Product p
                LEFT JOIN p.reviews r
                LEFT JOIN p.promotion po
                LEFT JOIN p.productVariations pr
                WHERE p.id = :productId
                GROUP BY p.id, p.name, p.createdAt, p.price
            """)
    ProductDetailResponse findProductDetailById(@Param("productId") Integer productId);

    @Query(value = """
                SELECT
                    p.id,
                    p.name,
                    ip.image_url AS image,
                    CAST(COALESCE(AVG(r.rating * 1.0), 0.0) AS DOUBLE) AS average_rating,
                    COUNT(DISTINCT r.id) AS review_count,
                    CAST(COALESCE(MIN(pr.price), p.price) AS double),
                    CAST(COALESCE(MAX(pr.price), p.price) AS double)
                FROM products p
                LEFT JOIN (
                    SELECT product_id, image_url,
                           ROW_NUMBER() OVER (PARTITION BY product_id ORDER BY id) AS rn
                    FROM image_products
                ) ip ON ip.product_id = p.id AND ip.rn = 1
                LEFT JOIN reviews r ON r.product_id = p.id
                LEFT JOIN promotions po ON po.id = p.promotion_id
                LEFT JOIN product_variations pr ON pr.product_id = p.id
                WHERE p.is_deleted = :isDeleted
                GROUP BY p.id, p.name, ip.image_url
            """, countQuery = """
                SELECT COUNT(DISTINCT p.id)
                FROM products p
                WHERE p.is_deleted = :isDeleted
            """, nativeQuery = true)
    Page<ProductSummaryResponse> findProductSummariesByDeletedStatus(
            @Param("isDeleted") boolean isDeleted,
            Pageable pageable);

    @Query(value = "SELECT p.name FROM order_details od " +
            "JOIN products p ON p.id = od.product_id " +
            "WHERE od.order_id = :orderId LIMIT 1", nativeQuery = true)
    List<String> findProductNamesByOrderId(@Param("orderId") int orderId);

    boolean existsByNameAndIsDeletedIsNot(String name, Integer isDeleted);

    @Query(value = """
                SELECT
                    p.id,
                    p.name,
                    ip.image_url AS image,
                    CAST(COALESCE(AVG(r.rating * 1.0), 0.0) AS DOUBLE) AS average_rating,
                    COUNT(DISTINCT r.id) AS review_count,
                    CAST(COALESCE(MIN(pr.price), p.price) AS double),
                    CAST(COALESCE(MAX(pr.price), p.price) AS double)
                FROM products p
                LEFT JOIN (
                    SELECT product_id, image_url,
                           ROW_NUMBER() OVER (PARTITION BY product_id ORDER BY id) AS rn
                    FROM image_products
                ) ip ON ip.product_id = p.id AND ip.rn = 1
                LEFT JOIN reviews r ON r.product_id = p.id
                LEFT JOIN promotions po ON po.id = p.promotion_id
                LEFT JOIN product_variations pr ON pr.product_id = p.id
                WHERE p.id = :productId
                GROUP BY p.id, p.name, ip.image_url
            """, nativeQuery = true)
    ProductSummaryResponse findProductSummaryById(@Param("productId") Integer productId);

    @Query(value = """
            SELECT
                p.id,
                p.name,
                ip.image_url AS image,
                CAST(COALESCE(AVG(r.rating * 1.0), 0.0) AS DOUBLE) AS average_rating,
                COUNT(DISTINCT r.id) AS review_count,
                CAST(COALESCE(MIN(pr.price), p.price) AS double),
                CAST(COALESCE(MAX(pr.price), p.price) AS double)
            FROM products p
            LEFT JOIN (
                SELECT product_id, image_url,
                       ROW_NUMBER() OVER (PARTITION BY product_id ORDER BY id) AS rn
                FROM image_products
            ) ip ON ip.product_id = p.id AND ip.rn = 1
            LEFT JOIN reviews r ON r.product_id = p.id
            LEFT JOIN promotions po ON po.id = p.promotion_id
            LEFT JOIN product_variations pr ON pr.product_id = p.id
            WHERE p.is_deleted = 0
              AND (:categoryId IS NULL OR (p.category_id IS NOT NULL AND p.category_id = :categoryId))
              AND (:query IS NULL OR (p.name LIKE CONCAT('%', :query, '%')))
            GROUP BY p.id, p.name, ip.image_url
            ORDER BY RAND()
            """, nativeQuery = true)
    List<ProductSummaryResponse> findRandomProduct(
            @Param("categoryId") Integer categoryId,
            @Param("query") String query,
            @Param("min") Integer min,
            @Param("max") Integer max,
            Pageable pageable);

    @Query(value = """
                SELECT
                    p.id,
                    p.name,
                    ip.image_url AS image,
                    CAST(COALESCE(AVG(r.rating * 1.0), 0.0) AS DOUBLE) AS average_rating,
                    COUNT(DISTINCT r.id) AS review_count,
                    CAST(COALESCE(MIN(pr.price), p.price) AS double),
                    CAST(COALESCE(MAX(pr.price), p.price) AS double)
                FROM products p
                LEFT JOIN (
                    SELECT product_id, image_url,
                           ROW_NUMBER() OVER (PARTITION BY product_id ORDER BY id) AS rn
                    FROM image_products
                ) ip ON ip.product_id = p.id AND ip.rn = 1
                LEFT JOIN reviews r ON r.product_id = p.id
                LEFT JOIN promotions po ON po.id = p.promotion_id
                LEFT JOIN product_variations pr ON pr.product_id = p.id
                WHERE p.is_deleted = 0
                  AND p.created_at >= :startDate
                  AND p.created_at <= :endDate
                GROUP BY p.id, p.name, ip.image_url
                ORDER BY p.created_at DESC
            """, countQuery = """
                SELECT COUNT(DISTINCT p.id)
                FROM products p
                WHERE p.is_deleted = 0
                  AND p.created_at >= :startDate
                  AND p.created_at <= :endDate
            """, nativeQuery = true)
    Page<ProductSummaryResponse> findProductsByDateRange(
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);

    @Query(value = """
                    SELECT p.* FROM products p
                    LEFT JOIN promotions promo ON p.promotion_id = promo.id
                    WHERE p.is_deleted = 0
                        AND promo.discount_value_percent IS NOT NULL
                        AND promo.is_active = true
                        AND promo.is_show_at_home = true
                    ORDER BY RAND()
                    LIMIT 7
            """, nativeQuery = true)
    List<Product> findRandomTop7ByPromotion();

}
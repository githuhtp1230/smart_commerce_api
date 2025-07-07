package com.shop.smart_commerce_api.repositories;

import com.shop.smart_commerce_api.dto.response.cart.CartItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.smart_commerce_api.entities.CartDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    @Query(value = """
        SELECT
            cd.id AS id,
            cd.product_id AS productId,
            cd.product_variation_id AS productVariationId,
            COALESCE(pavi_img.image_url, ip_img.image_url) AS image,
            cd.quantity AS quantity
        FROM cart_details cd
        LEFT JOIN (
            SELECT pva.product_variation_id, pavi.image_url
            FROM product_variation_attributes pva
            JOIN product_attribute_value_images pavi ON pavi.attribute_value_id = pva.attribute_value_id
        ) pavi_img ON pavi_img.product_variation_id = cd.product_variation_id
        LEFT JOIN (
            SELECT ip1.product_id, ip1.image_url
            FROM image_products ip1
            JOIN (
                SELECT product_id, MIN(id) AS min_id
                FROM image_products
                GROUP BY product_id
            ) ip2 ON ip1.product_id = ip2.product_id AND ip1.id = ip2.min_id
        ) ip_img ON ip_img.product_id = cd.product_id
        WHERE cd.user_id = :userId
        """, nativeQuery = true)
    List<CartItemResponse> getCartItems(@Param("userId") Integer userId);
}

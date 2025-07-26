package com.shop.smart_commerce_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.smart_commerce_api.dto.response.order.OrderDetailResponse;
import com.shop.smart_commerce_api.entities.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query(value = """
                SELECT
                    od.id AS id,
                    od.product_id AS productId,
                    od.product_variation_id AS productVariationId,
                    COALESCE(pavi_img.image_url, ip_img.image_url) AS image,
                    od.quantity AS quantity,
                    od.price AS price
                FROM order_details od
                LEFT JOIN (
                    SELECT pva.product_variation_id, pavi.image_url
                    FROM product_variation_attributes pva
                    JOIN product_attribute_value_images pavi ON pavi.attribute_value_id = pva.attribute_value_id
                ) pavi_img ON pavi_img.product_variation_id = od.product_variation_id
                LEFT JOIN (
                    SELECT ip1.product_id, ip1.image_url
                    FROM image_products ip1
                    JOIN (
                        SELECT product_id, MIN(id) AS min_id
                        FROM image_products
                        GROUP BY product_id
                    ) ip2 ON ip1.product_id = ip2.product_id AND ip1.id = ip2.min_id
                ) ip_img ON ip_img.product_id = od.product_id
                WHERE od.order_id = :orderId
            """, nativeQuery = true)
    List<OrderDetailResponse> getOrderDetails(@Param("orderId") Integer orderId);

    @Query("SELECT od FROM OrderDetail od WHERE od.order.id = :orderId AND od.product.id = :productId AND (:productVariationId IS NULL OR od.productVariation.id = :productVariationId)")
    OrderDetail getOrderDetailByOrderIdAndProductIdOrProductVariationId(
            @Param("orderId") Integer orderId,
            @Param("productId") Integer productId,
            @Param("productVariationId") Integer productVariationId);

    List<OrderDetail> findByOrderId(Integer orderId);
}

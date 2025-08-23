package com.shop.smart_commerce_api.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.smart_commerce_api.constant.OrderStatus;
import com.shop.smart_commerce_api.dto.query.order.OrderSummary;
import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderSummaryResponse;
import com.shop.smart_commerce_api.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    OrderResponse findOrderById(Integer orderId);

    List<Order> findByUserId(Integer userId);

    // @Query("""
    // SELECT new com.shop.smart_commerce_api.dto.query.order.OrderSummary(o,
    // SUM(od.price))
    // FROM Order o
    // LEFT JOIN o.orderDetails od
    // WHERE od.id IN :ids
    // GROUP BY o
    // """)
    // List<OrderSummary> findOrderSummaries(@Param("ids") List<Integer> ids);

    Page<Order> findByUserId(Integer userId, Pageable pageable);

    Page<Order> findByStatusAndUserId(String status, Integer userId, Pageable pageable);

    // Returns Object[]{totalProducts, totalAmount}
    @Query("SELECT COALESCE(SUM(od.quantity),0), COALESCE(SUM(od.quantity * od.price),0) FROM Order o JOIN o.orderDetails od WHERE o.user.id = :userId AND (:from IS NULL OR o.createdAt >= :from)")
    Object getUserOrderStatistics(@Param("userId") Integer userId,
            @Param("from") LocalDateTime from);

    // Returns Object[]{totalSold, totalRevenue}
    @Query("SELECT COALESCE(SUM(od.quantity),0), COALESCE(SUM(od.quantity * od.price),0) FROM Order o JOIN o.orderDetails od WHERE (:productId IS NULL OR od.product.id = :productId) AND (:from IS NULL OR o.createdAt >= :from)")
    Object getProductStatistical(@Param("productId") Integer productId,
            @Param("from") java.time.LocalDateTime from);

    // Returns Object[]{totalSold, totalRevenue}
    @org.springframework.data.jpa.repository.Query("SELECT COALESCE(SUM(od.quantity),0), COALESCE(SUM(od.quantity * od.price),0) FROM Order o JOIN o.orderDetails od JOIN od.product p WHERE (:categoryId IS NULL OR p.category.id = :categoryId) AND (:from IS NULL OR o.createdAt >= :from)")
    Object getCategoryStatistical(@org.springframework.data.repository.query.Param("categoryId") Integer categoryId,
            @org.springframework.data.repository.query.Param("from") java.time.LocalDateTime from);
}

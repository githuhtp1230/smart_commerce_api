package com.shop.smart_commerce_api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.smart_commerce_api.dto.query.order.OrderSummary;
import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.entities.Order;
import com.shop.smart_commerce_api.model.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    OrderResponse findOrderById(Integer orderId);

    List<Order> findByUserId(Integer userId);

    List<Order> findByStatusAndUserId(OrderStatus status, Integer userId);

    @Query("""
                SELECT new com.shop.smart_commerce_api.dto.query.order.OrderSummary(o, SUM(od.price))
                FROM Order o
                LEFT JOIN o.orderDetails od
                WHERE od.id IN :ids
                GROUP BY o
            """)
    List<OrderSummary> findOrderSummaries(@Param("ids") List<Integer> ids);
}

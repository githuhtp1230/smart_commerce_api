package com.shop.smart_commerce_api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.entities.Order;
import com.shop.smart_commerce_api.model.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    OrderResponse findOrderById(Integer orderId);

    List<Order> findByUserId(Integer userId);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

}

package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;

import com.shop.smart_commerce_api.dto.request.order.AddOrderDetailRequest;
import com.shop.smart_commerce_api.dto.request.order.AddOrderRequest;
import com.shop.smart_commerce_api.dto.response.order.OrderDetailResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.entities.Order;
import com.shop.smart_commerce_api.entities.OrderDetail;
import com.shop.smart_commerce_api.model.OrderStatus;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

    Order toOrder(Order order);

    public OrderResponse toOrderResponse(Order order);

    OrderDetail toOrderDetail(AddOrderDetailRequest request);
}

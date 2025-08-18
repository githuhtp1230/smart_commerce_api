package com.shop.smart_commerce_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.shop.smart_commerce_api.dto.request.order.AddOrderDetailRequest;
import com.shop.smart_commerce_api.dto.request.order.AddOrderRequest;
import com.shop.smart_commerce_api.dto.response.order.OrderDetailResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderResponse;
import com.shop.smart_commerce_api.dto.response.order.OrderSummaryResponse;
import com.shop.smart_commerce_api.entities.Order;
import com.shop.smart_commerce_api.entities.OrderDetail;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

    Order toOrder(Order order);

    public OrderResponse toOrderResponse(Order order);

    OrderDetail toOrderDetail(AddOrderDetailRequest request);

    List<OrderResponse> toOrderResponseList(List<Order> orders);

    OrderSummaryResponse toOrderSummaryResponse(Order order);

}

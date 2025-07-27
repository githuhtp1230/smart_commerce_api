package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;

import com.shop.smart_commerce_api.dto.request.order.AddOrderDetailRequest;
import com.shop.smart_commerce_api.entities.OrderDetail;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetail toOrderDetail(AddOrderDetailRequest request);

}

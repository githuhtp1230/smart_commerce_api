package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;

import com.shop.smart_commerce_api.dto.request.cart.AddCartItemRequest;
import com.shop.smart_commerce_api.entities.CartDetail;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDetail toCartDetail(AddCartItemRequest request);
}

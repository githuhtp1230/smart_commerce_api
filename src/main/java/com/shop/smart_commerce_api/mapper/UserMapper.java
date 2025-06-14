package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;

import com.shop.smart_commerce_api.dto.response.user.UserResponse;
import com.shop.smart_commerce_api.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}

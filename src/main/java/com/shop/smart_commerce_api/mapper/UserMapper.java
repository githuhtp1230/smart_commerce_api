package com.shop.smart_commerce_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.shop.smart_commerce_api.dto.request.user.UserRequest;
import com.shop.smart_commerce_api.dto.response.user.UserResponse;
import com.shop.smart_commerce_api.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", source = "role.name")
    UserResponse toUserResponse(User user);

    User toUser(UserRequest request);

    void updateUserFromRequest(UserRequest request, @MappingTarget User user);
}

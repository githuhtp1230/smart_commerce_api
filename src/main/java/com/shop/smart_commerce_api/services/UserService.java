package com.shop.smart_commerce_api.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.smart_commerce_api.dto.response.user.UserResponse;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.UserMapper;
import com.shop.smart_commerce_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserResponse getCurrentProfile() {
        return userMapper.toUserResponse(getCurrentUser());
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public UserResponse toggleIsActiveUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setIsActive(!user.getIsActive());
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}

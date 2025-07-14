package com.shop.smart_commerce_api.services;

import java.util.List;

import org.hibernate.sql.Update;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.smart_commerce_api.dto.request.user.UserRequest;
import com.shop.smart_commerce_api.dto.request.user.UserUpdateProfileRequest;
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
    private final PasswordEncoder passwordEncoder;

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

    public UserResponse updateProfile(UserUpdateProfileRequest request) {
        User user = getCurrentUser();

        if (request.getName() != null) {
            user.setName(request.getName());

        }
        if (request.getAvatar() != null) {
            user.setAvatar(request.getAvatar());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse create(UserRequest request) {
        User user = userMapper.toUser(request);
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        user.setIsActive(true);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse update(int id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUserFromRequest(request, user);
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getAll() {
        var users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }
}

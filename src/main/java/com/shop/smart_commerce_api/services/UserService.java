package com.shop.smart_commerce_api.services;

import java.util.List;

import org.hibernate.sql.Update;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.smart_commerce_api.dto.request.auth.MailContactRequest;
import com.shop.smart_commerce_api.dto.request.filter.ProductSummaryFilterRequest;
import com.shop.smart_commerce_api.dto.request.filter.UserFilterRequest;
import com.shop.smart_commerce_api.dto.request.user.CreateUserRequest;
import com.shop.smart_commerce_api.dto.request.user.UpdateUserRequest;
import com.shop.smart_commerce_api.dto.request.user.UserUpdateProfileRequest;
import com.shop.smart_commerce_api.dto.response.address.AddressResponse;
import com.shop.smart_commerce_api.dto.response.user.UserResponse;
import com.shop.smart_commerce_api.entities.Role;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.UserMapper;
import com.shop.smart_commerce_api.repositories.RoleRepository;
import com.shop.smart_commerce_api.repositories.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String contactEmail;

    public UserResponse getCurrentProfile() {
        UserResponse userResponse = userMapper.toUserResponse(getCurrentUser());
        return userResponse;
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

    public List<UserResponse> getAllUsers(UserFilterRequest request) {
        if (request.getIsCustomer() != null && request.getIsCustomer()) {
            return userMapper.toUserResponseList(userRepository.findAllCustomers());
        } else if (request.getIsMemberShip() != null && request.getIsMemberShip()) {
            return userMapper.toUserResponseList(userRepository.findAllMemberships());
        }
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    public UserResponse createUser(CreateUserRequest request) {
        User existed = userRepository.findByEmail(request.getEmail());
        if (existed != null) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        Role role;
        if (request.getRoleId() != null) {
            role = roleRepository.findById(request.getRoleId()).orElse(null);
        } else {
            role = roleRepository.findByName("USER");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .avatar(request.getAvatar())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .isActive(Boolean.TRUE)
                .build();
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(UpdateUserRequest request) {
        if (request.getId() == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (request.getName() != null)
            user.setName(request.getName());
        if (request.getEmail() != null)
            user.setEmail(request.getEmail());
        if (request.getPhone() != null)
            user.setPhone(request.getPhone());
        if (request.getAvatar() != null)
            user.setAvatar(request.getAvatar());
        if (request.getPassword() != null)
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getRoleId() != null) {
            Role role = roleRepository.findById(request.getRoleId()).orElse(null);
            user.setRole(role);
        }
        if (request.getIsActive() != null)
            user.setIsActive(request.getIsActive());

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse sendEmailContact(MailContactRequest request) {
        User user = getCurrentUser();
        String email = user.getEmail();
        if (email == null || email.isEmpty()) {
            throw new AppException(ErrorCode.EMAIL_NOT_FOUND);
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(contactEmail); // Địa chỉ nhận liên hệ lấy từ cấu hình
            helper.setSubject(request.getTitle());
            helper.setText(request.getMessage() + "<br>From: " + email, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }
        return userMapper.toUserResponse(user);
    }
}

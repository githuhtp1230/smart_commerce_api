package com.shop.smart_commerce_api.services;

import java.io.UnsupportedEncodingException;
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

    public UserResponse updateUser(Integer userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
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
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(contactEmail);
            helper.setFrom("noreply@yourdomain.com", user.getName());
            helper.setReplyTo(email);
            helper.setSubject("[Customer Support] " + request.getTitle());
            String content = "<html>" +
                    "<body style='font-family: Arial, sans-serif;'>" +
                    "<h2 style='color:#2d89ef;'>📩 New Contact Request</h2>" +
                    "<p><b>From:</b> " + user.getName() + " (" + email + ")</p>" +
                    "<p><b>Title:</b> " + request.getTitle() + "</p>" +
                    "<p><b>Message:</b></p>" +
                    "<div style='margin:10px 0; padding:10px; border:1px solid #ddd; background:#f9f9f9;'>" +
                    request.getMessage().replace("\n", "<br>") +
                    "</div>" +
                    "<hr>" +
                    "<p style='font-size:12px;color:#888;'>This email was sent automatically from Smart Commerce Contact Form.</p>"
                    +
                    "</body>" +
                    "</html>";

            helper.setText(content, true);

            mailSender.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }

        return userMapper.toUserResponse(user);
    }

}

package com.shop.smart_commerce_api.services;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.auth.LoginRequest;
import com.shop.smart_commerce_api.dto.request.auth.RegisterRequest;
import com.shop.smart_commerce_api.dto.request.otp.RegisterOtpRequest;
import com.shop.smart_commerce_api.dto.response.auth.LoginResponse;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.mapper.UserMapper;
import com.shop.smart_commerce_api.model.OtpSession;
import com.shop.smart_commerce_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtGeneratorService jwtGeneratorService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtGeneratorService.generateAccessToken(user);
            return LoginResponse.builder()
                    .user(userMapper.toUserResponse(user))
                    .accessToken(accessToken)

                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.EMAIL_OR_PASSWORD_INVALID);
        }
    }

    public void register(RegisterRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user != null) {
            throw new AppException(ErrorCode.USER_EXSITS);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String otp = generateOtp();
        OtpSession otpSession = OtpSession.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .otp(otp)
                .build();
        redisService.saveObject(request.getEmail(), otpSession, 3);

        Map<String, String> models = new HashMap<>();
        models.put("otp", otp);
        models.put("expiryMinutes", 3 + "");
        mailService.sendEmail(request.getEmail(), "Xác nhận đăng ký email", models,
                "otp-email");
    }

    public void verifyOtp(RegisterOtpRequest request) {
        try {
            OtpSession otpSession = (OtpSession) redisService.getObject(request.getEmail());
            if (!request.getOtp().equals(otpSession.getOtp())) {
                throw new AppException(ErrorCode.OTP_WRONG);
            }
            if (request.getEmail().equals(otpSession.getEmail())) {
                User user = User.builder()
                        .name(otpSession.getUsername())
                        .email(otpSession.getEmail())
                        .password(otpSession.getPassword())
                        .build();
                userRepository.save(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.VERIFY_OTP_FAILED);
        }
    }

    private String generateOtp() {
        int otp = new SecureRandom().nextInt(900000) + 100000;
        return String.valueOf(otp);
    }

}

package com.shop.smart_commerce_api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpSession {
    private String username;
    private String email;
    private String phone;
    private String password;
    private String otp;
}

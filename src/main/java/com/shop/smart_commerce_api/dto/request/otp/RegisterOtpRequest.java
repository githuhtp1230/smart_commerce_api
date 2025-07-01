package com.shop.smart_commerce_api.dto.request.otp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterOtpRequest {
    private String otp;
    private String email;
}

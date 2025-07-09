package com.shop.smart_commerce_api.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {
    private String email;
    private String otp;
}

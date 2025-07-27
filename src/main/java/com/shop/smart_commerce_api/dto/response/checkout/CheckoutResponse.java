package com.shop.smart_commerce_api.dto.response.checkout;

import com.shop.smart_commerce_api.dto.response.payment.InitPaymentResponse;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutResponse {
    InitPaymentResponse payment;
}

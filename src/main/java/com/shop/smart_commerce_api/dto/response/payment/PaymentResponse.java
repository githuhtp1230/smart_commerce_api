package com.shop.smart_commerce_api.dto.response.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private Integer id;
    private String name;
    private String code;
}

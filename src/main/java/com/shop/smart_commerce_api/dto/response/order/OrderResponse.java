package com.shop.smart_commerce_api.dto.response.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shop.smart_commerce_api.dto.response.payment.PaymentResponse;
import com.shop.smart_commerce_api.dto.response.voucher.VoucherResponse;
import com.shop.smart_commerce_api.entities.User;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Integer id;
    private VoucherResponse voucher;
    private PaymentResponse payment;
    private Integer total;
    private String status;
    private String createdAt;
}

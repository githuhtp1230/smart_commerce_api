package com.shop.smart_commerce_api.dto.response.order;

import java.util.List;

import com.shop.smart_commerce_api.dto.response.payment.PaymentResponse;
import com.shop.smart_commerce_api.dto.response.voucher.VoucherResponse;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryResponse {
    private Integer id;
    private List<OrderDetailResponse> orderDetails;
    private VoucherResponse voucher;
    private PaymentResponse payment;
    private Integer total;
    private String status;
    private String createdAt;
    private String productImage;
    private String address;
}

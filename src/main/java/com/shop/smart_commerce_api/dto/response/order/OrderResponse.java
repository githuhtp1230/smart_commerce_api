package com.shop.smart_commerce_api.dto.response.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shop.smart_commerce_api.dto.response.payment.PaymentResponse;
import com.shop.smart_commerce_api.dto.response.voucher.VoucherResponse;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.model.OrderStatus;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Integer id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private VoucherResponse voucher;
    private PaymentResponse payment;
    private Integer total;
    private Integer status;
    private String createdAt;

    public OrderResponse(Integer id, Integer voucherId, Integer paymentId, Integer total,
            Integer status, String createdAt) {
        this.id = id;
        this.voucher = voucherId != null
                ? VoucherResponse.builder().id(voucherId).build()
                : null;
        this.payment = PaymentResponse.builder().id(paymentId).build();
        this.total = total;
        this.status = status;
        this.createdAt = createdAt;
    }
}

package com.shop.smart_commerce_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.shop.smart_commerce_api.dto.response.payment.PaymentResponse;
import com.shop.smart_commerce_api.entities.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponse toPaymentResponse(Payment payment);

    List<PaymentResponse> toPaymentResponses(List<Payment> payments);
}

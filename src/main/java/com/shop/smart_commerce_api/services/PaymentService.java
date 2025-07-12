package com.shop.smart_commerce_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.response.payment.PaymentResponse;
import com.shop.smart_commerce_api.mapper.PaymentMapper;
import com.shop.smart_commerce_api.repositories.PaymentRepository;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public List<PaymentResponse> getAllPayments() {
        return paymentMapper.toPaymentResponses(paymentRepository.findAll());
    }
}

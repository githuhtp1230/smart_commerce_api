package com.shop.smart_commerce_api.services;

import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.dto.request.checkout.CheckoutRequest;
import com.shop.smart_commerce_api.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final OrderRepository orderRepository;

    public void checkout(CheckoutRequest request) {
        request.getCartItemIds().stream().map(cartItemId -> {

        }).toList();
    }
}

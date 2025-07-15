package com.shop.smart_commerce_api.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.smart_commerce_api.services.VNPayService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/vnpay")
public class VNPayController {

    @Autowired
    private VNPayService vnPayService;

    @GetMapping("/create-payment")
        public ResponseEntity<?> createPayment(@RequestParam int amount,
                                       @RequestParam String orderId,
                                       HttpServletRequest request) {
            try {
                String paymentUrl = vnPayService.createPaymentUrl(request, amount, orderId);
                return ResponseEntity.ok(Collections.singletonMap("paymentUrl", paymentUrl));
            } catch (Exception e) {
                return ResponseEntity.status(500)
                        .body(Collections.singletonMap("error", "Lỗi tạo URL thanh toán: " + e.getMessage()));
            }
        }

}

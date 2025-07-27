package com.shop.smart_commerce_api.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import com.shop.smart_commerce_api.util.EncodingUtil;

import jakarta.annotation.PostConstruct;

@Service
public class CryptoService {
    public CryptoService() throws NoSuchAlgorithmException {
    }

    @Value("${payment.vnpay.secret-key}")
    private String secretKey;

    private final Mac mac = Mac.getInstance("HmacSHA512");

    @PostConstruct
    void init() throws InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA512");
        mac.init(secretKeySpec);
    }

    public String sign(String data) {
        try {
            return EncodingUtil.toHexString(mac.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new AppException(ErrorCode.VNPAY_SIGNING_FAILED);
        }
    }
}

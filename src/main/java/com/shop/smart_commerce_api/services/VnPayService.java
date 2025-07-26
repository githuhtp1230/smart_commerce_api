package com.shop.smart_commerce_api.services;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shop.smart_commerce_api.constant.VNPayParams;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VnPayService {
    public static final String VERSION = "2.1.0";
    public static final String COMMAND = "pay";
    public static final String ORDER_TYPE = "190000";
    public static final long DEFAULT_MULTIPLIER = 100L;

    @Value("${payment.vnpay.tmn-code}")
    private String tmnCode;

    @Value("${payment.vnpay.init-payment-url}")
    private String initPaymentPrefixUrl;

    @Value("${payment.vnpay.return-url}")
    private String returnUrlFormat;

    @Value("${payment.vnpay.timeout}")
    private Integer paymentTimeout;

    private final CryptoService cryptoService;

    public void init() {
        Map<String, String> params = new HashMap<>();

        Calendar vnCalendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        var ipAddress = re

        params.put(VNPayParams.VERSION, VERSION);
        params.put(VNPayParams.COMMAND, COMMAND);

        params.put(VNPayParams.TMN_CODE, tmnCode);
        params.put(VNPayParams.AMOUNT, String.valueOf(100));
        params.put(VNPayParams.CURRENCY, Currency.VND.getValue());

        params.put(VNPayParams.TXN_REF, txnRef);
        params.put(VNPayParams.RETURN_URL, returnUrl);

        params.put(VNPayParams.CREATED_DATE, createdDate);
        params.put(VNPayParams.EXPIRE_DATE, expiredDate);

        params.put(VNPayParams.IP_ADDRESS, ipAddress);
        params.put(VNPayParams.LOCALE, Locale.VIETNAM.getCode());

        params.put(VNPayParams.ORDER_INFO, orderInfo);
        params.put(VNPayParams.ORDER_TYPE, ORDER_TYPE);
    }
}

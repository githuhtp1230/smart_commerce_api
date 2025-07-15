package com.shop.smart_commerce_api.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "payment.vnpay")
public class VnPayProperties {
    private String tmnCode;
    private String secretKey;
    private String initPaymentUrl;
    private String returnUrl;
    private int timeout;
}

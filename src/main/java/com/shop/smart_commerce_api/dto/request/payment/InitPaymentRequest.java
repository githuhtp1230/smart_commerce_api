package com.shop.smart_commerce_api.dto.request.payment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InitPaymentRequest {

    private String requestId;

    private String ipAddress;

    private Integer userId;

    private String txnRef;

    private long amount;

}

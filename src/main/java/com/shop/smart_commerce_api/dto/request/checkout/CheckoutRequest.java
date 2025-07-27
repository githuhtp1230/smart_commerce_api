package com.shop.smart_commerce_api.dto.request.checkout;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckoutRequest {
    private List<Integer> cartItemIds;
    private Integer addressId;
    private Integer paymentId;
    private String ipAddress;
}
